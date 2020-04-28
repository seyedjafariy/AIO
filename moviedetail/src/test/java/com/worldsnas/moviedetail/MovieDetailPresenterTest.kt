package com.worldsnas.moviedetail

import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import com.worldsnas.kotlintesthelpers.rule.RxTrampolineSchedulerRule
import com.worldsnas.navigation.model.MovieDetailLocalModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailPresenterTest {

    private val repo = mockk<MovieDetailRepo>(relaxed = true)

    private lateinit var presenter: MovieDetailPresenter
    private lateinit var processor: MovieDetailProcessor

    @get:Rule
    val rxRule = RxTrampolineSchedulerRule()

    @Before
    fun setUp() {
        clearMocks(repo)
//        processor = MovieDetailProcessor(repo)
//        presenter = MovieDetailPresenter(processor)
    }

    @Test
    fun `initial intent returns correct title`() {
        val name = randomString()
        every {
            repo.getMovieDetail(any())
        } returns Single.just(
            MovieDetailRepoOutPutModel.Success(
                MovieRepoModel(
                    title = name
                )
            )
        )

        val testObserver = presenter.states()
            .test()

        presenter.processIntents(
            MovieDetailIntent.Initial(
                MovieDetailLocalModel(
                    randomLong(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString()
                )
            )
        )

        val values = testObserver.values()

        assertThat(values)
            .usingRecursiveFieldByFieldElementComparator()
            .isEqualTo(
                listOf(
                    MovieDetailState.start(),
                    MovieDetailState.start().copy(
                        title = name
                    )
                )
            )
    }
}