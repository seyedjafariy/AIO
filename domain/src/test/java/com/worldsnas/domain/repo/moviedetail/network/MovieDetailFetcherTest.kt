package com.worldsnas.domain.repo.moviedetail.network

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.kotlintesthelpers.randomLong
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MovieDetailFetcherTest {

    private val api = mockk<MovieDetailAPI>(relaxed = true)
    private lateinit var fetcher: MovieDetailFetcher

    @Before
    fun setUp() {
        clearMocks(api)

        fetcher = MovieDetailFetcher(api)
    }

    @Test
    fun `fetches data correctly`() {

        val param = MovieDetailRequestModel(
            randomLong()
        )

        every {
            api.getMovie(param.movieId, param.appendToResponse)
        } returns Single.just(Response.success<MovieServerModel>(200, null))

        val testObserver = fetcher.fetch(param).test()

        testObserver.assertNoErrors()

        val values = testObserver.values()

        assertThat(values[0].code())
            .isEqualTo(200)
    }
}