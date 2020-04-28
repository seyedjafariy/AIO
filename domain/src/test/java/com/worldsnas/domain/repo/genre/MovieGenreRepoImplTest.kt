package com.worldsnas.domain.repo.genre

import com.squareup.moshi.Moshi
import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.mappers.server.GenreServerRepoMapper
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.AppJsonAdapterFactory
import com.worldsnas.domain.model.servermodels.GenreListServerModel
import com.worldsnas.kotlintesthelpers.helpers.getJson
import com.worldsnas.kotlintesthelpers.rule.RxTestSchedulerRule
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MovieGenreRepoImplTest {

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

    private val mapper = GenreServerRepoMapper()
    private val api : GenreAPI = retrofit.create()

    private lateinit var repo: MovieGenreRepoImpl

    private val server = MockWebServer()


    @Before
    fun setUp() {
        server.start(8080)

        repo = MovieGenreRepoImpl(api, mapper)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `correct genres received on 200`() {

        val actualModels = moshi.adapter<GenreListServerModel>(
            GenreListServerModel::class.java
        ).fromJson(
            getJson("json/Genres.json")
        )!!.genres
            .map {
                mapper.map(it)
            }

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/Genres.json"))
        )

        val testObserver = repo.fetchAllGenre()
            .test()

        RxTestSchedulerRule.TEST_SCHEDULER_INSTANCE.triggerActions()

        val values = testObserver
            .awaitCount(1)
            .assertValueCount(1)
            .assertComplete()
            .values()

        val repos: List<GenreRepoModel> = values.first().foldRight(
            emptyList<GenreRepoModel>()
        ) { body, _ ->
            body
        }


        Assertions.assertThat(repos)
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

        val testObserver = repo.fetchAllGenre()
            .test()


        val values = testObserver
            .awaitCount(1)
            .assertValueCount(1)
            .assertComplete()
            .values()

        when (val either = values.first()) {
            is Either.Left ->
                Assertions.assertThat(either.a)
                    .isEqualToComparingFieldByFieldRecursively(actualError)
            is Either.Right ->
                throw IllegalStateException(
                    "server returned error"
                )
        }
    }

    @Test
    fun `correctly caches first page`() {
        val actualModels = moshi.adapter<GenreListServerModel>(
            GenreListServerModel::class.java
        ).fromJson(
            getJson("json/Genres.json")
        )!!.genres
            .map {
                mapper.map(it)
            }

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getJson("json/Genres.json"))
        )

        repo.fetchAllGenre()
            .test()
            .awaitCount(1)
            .assertComplete()

        RxTestSchedulerRule.TEST_SCHEDULER_INSTANCE.triggerActions()

        val values = repo.cachedGenre()
            .test()
            .awaitCount(1)
            .assertComplete()
            .values()

        assertThat(values.first()!!)
            .usingRecursiveFieldByFieldElementComparator()
            .containsExactlyElementsOf(actualModels)
    }
}