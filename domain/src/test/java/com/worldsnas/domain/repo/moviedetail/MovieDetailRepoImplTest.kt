package com.worldsnas.domain.repo.moviedetail

import com.squareup.moshi.Moshi
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.R
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.mappers.server.*
import com.worldsnas.domain.model.servermodels.AppJsonAdapterFactory
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRequestModel
import com.worldsnas.kotlintesthelpers.createRetrofitErrorResponse
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.RFetcher
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.intellij.lang.annotations.Language
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieDetailRepoImplTest {

    private val genreMapper = GenreServerRepoMapper()
    private val companyMapper = CompanyServerRepoMapper()
    private val countryMapper = CountryServerRepoMapper()
    private val languageMapper = LanguageServerRepoMapper()
    private val videoMapper = VideoServerRepoMapper()
    private val imageMapper = ImageServerRepoMapper()
    private val reviewMapper = ReviewServerRepoMapper()
    private val castMapper = CastServerRepoMapper()
    private val crewMapper = CrewServerRepoMapper()
    private val translationMapper = TranslationServerRepoMapper()

    private val mapper = MovieServerRepoMapper(
        genreMapper,
        companyMapper,
        countryMapper,
        languageMapper,
        videoMapper,
        imageMapper,
        reviewMapper,
        castMapper,
        crewMapper,
        translationMapper
    )

    private val fetcher = mockk<RFetcher<MovieDetailRequestModel, MovieServerModel>>(relaxed = true)

    private lateinit var repo: MovieDetailRepoImpl

    private val moshi = Moshi.Builder().add(AppJsonAdapterFactory.INSTANCE).build()

    @Before
    fun setUp() {
        clearMocks(fetcher)

        repo = MovieDetailRepoImpl(
            fetcher,
            mapper
        )
    }

    @Test
    fun `gets and maps movie correctly`() {

        @Language("JSON") val movieJson = "{\n  \n}"

        val movieServer = moshi
            .adapter<MovieServerModel>(MovieServerModel::class.java)
            .fromJson(movieJson)

        every {
            fetcher.fetch(any())
        } returns Single.just(
            Response.success(movieServer)
        )

        val testObserver = repo.getMovieDetail(
            MovieDetailRepoParamModel(
                randomLong()
            )
        ).test()

        testObserver.assertNoErrors()
        val model = testObserver.values()[0]

        assertThat(model)
            .isInstanceOf(MovieDetailRepoOutPutModel.Success::class.java)
            .extracting("movie")
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(arrayOf(mapper.map(movieServer!!)))
    }

    @Test
    fun `correctly returns error model for null response`() {
        val errorResponse = Response.success<MovieServerModel>(null)

        every {
            fetcher.fetch(any())
        } returns Single.just(
            errorResponse
        )

        val testObserver = repo.getMovieDetail(MovieDetailRepoParamModel(randomLong()))
            .test()

        testObserver.assertNoErrors()

        val value = testObserver.values()[0]

        assertThat(value)
            .isInstanceOf(MovieDetailRepoOutPutModel.Error::class.java)
            .extracting("err")
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(
                arrayOf(
                    ErrorHolder.Res(
                        R.string.error_no_item_received,
                        errorResponse.code()
                    )
                )
            )
    }

    @Test
    fun `correctly returns error model`() {
        val error = randomString()
        val errorResponse = createRetrofitErrorResponse<MovieServerModel>(404, error)

        every {
            fetcher.fetch(any())
        } returns Single.just(
            errorResponse
        )

        val testObserver = repo.getMovieDetail(MovieDetailRepoParamModel(randomLong()))
            .test()

        testObserver.assertNoErrors()

        val value = testObserver.values()[0]

        assertThat(value)
            .isInstanceOf(MovieDetailRepoOutPutModel.Error::class.java)
            .extracting("err")
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(
                arrayOf(
                    createRetrofitErrorResponse<MovieServerModel>(404, error)
                        .getErrorRepoModel()
                )
            )
    }
}