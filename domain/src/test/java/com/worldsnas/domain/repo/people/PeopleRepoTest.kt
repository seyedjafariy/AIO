package com.worldsnas.domain.repo.people

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.mappers.PersonServerRepoMapper
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.kotlintesthelpers.helpers.getJson
import com.worldsnas.kotlintesthelpers.rule.RxTestSchedulerRule
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PeopleRepoTest {

    @get:Rule
    val rule = RxTestSchedulerRule()

    private val moshi = Moshi.Builder()
        .add(AppJsonAdapterFactory.INSTANCE)
        .build()

    private val url = "http://localhost:8080/".toHttpUrl()
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()

    private val mapper = PersonServerRepoMapper()
    private val api: PeopleAPI = retrofit.create()
    private lateinit var repo: PeopleRepoImpl

    private val server = MockWebServer()

    @Before
    fun setup() {
        server.start(8080)

        repo = PeopleRepoImpl(api, mapper)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `success from repo`() {
        val resultType = Types.newParameterizedType(
            ResultsServerModel::class.java,
            PersonServerModel::class.java
        )
        val actualModels = moshi
            .adapter<ResultsServerModel<PersonServerModel>>(resultType)
            .fromJson(getJson(("json/People.json")))!!
            .list
            .map {
                mapper.map(it)
            }

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/People.json"))
        )

        val testObserver = repo.fetchPeople(1)
            .test()

        RxTestSchedulerRule.TEST_SCHEDULER_INSTANCE.triggerActions()

        val values = testObserver
            .awaitCount(1)
            .assertValueCount(1)
            .assertComplete()
            .values()

        val repos: List<PersonRepoModel> = values.first().foldRight(
            emptyList<PersonRepoModel>()
        ) { body, _ ->
            body
        }


        assertThat(repos)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyElementsOf(actualModels)
    }

    @Test
    fun `sends correct error when 404`() {
        val actualError =
            ErrorHolder.Message(
                "json failed",
                404
            )

        server.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(getJson("json/Sample404Error.json"))
        )

        val testObserver = repo.fetchPeople(1)
            .test()


        val values = testObserver
            .awaitCount(1)
            .assertValueCount(1)
            .assertComplete()
            .values()

        when (val either = values.first()) {
            is Either.Left ->
                assertThat(either.a)
                    .isEqualToComparingFieldByFieldRecursively(actualError)
            is Either.Right ->
                throw IllegalStateException(
                    "server returned error"
                )
        }
    }

    @Test
    fun `correctly caches first page`() {
        val resultType = Types.newParameterizedType(
            ResultsServerModel::class.java,
            PersonServerModel::class.java
        )
        val actualModels = moshi
            .adapter<ResultsServerModel<PersonServerModel>>(resultType)
            .fromJson(getJson(("json/People.json")))!!
            .list
            .map {
                mapper.map(it)
            }

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/People.json"))
        )

        repo.fetchPeople(1)
            .test()
            .awaitCount(1)
            .assertComplete()

        RxTestSchedulerRule.TEST_SCHEDULER_INSTANCE.triggerActions()

        val values = repo.cachedPeople()
            .test()
            .awaitCount(1)
            .assertComplete()
            .values()

        assertThat(values.first()!!)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyElementsOf(actualModels)
    }
}