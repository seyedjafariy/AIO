package com.worldsnas.home

import com.worldsnas.core.*
import com.worldsnas.core.mvi.BaseProcessor
import com.worldsnas.core.mvi.toErrorState
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import com.worldsnas.home.model.Movie
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.navigation.navigateTo
import kotlinx.coroutines.flow.*

class HomeProcessor(
    private val navigator: Navigator,
    latestRepo: LatestMovieRepo,
    trendingRepo: TrendingRepo,
    movieMapper: Mapper<MovieRepoModel, Movie>
) : BaseProcessor<HomeIntent, HomeResult>() {

    override fun transformers(): List<FlowBlock<HomeIntent, HomeResult>> = listOf(
            latestProcessor,
            trendingProcessor,
            nextPageProcessor,
            latestClickProcessor,
            sliderClicked,
            searchClicked
        )

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val initialProcessor: Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.Initial>().flatMapLatest {
            listOf(
                flowOf(it).let(latestProcessor),
                flowOf(it).let(trendingProcessor)
            ).merge()
        }
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val latestProcessor: Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.Initial>().map {
            PageModel.First
        }
            .let(latestMovieProcessor)
    }

    private val trendingProcessor: Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.Initial>().flatMapMerge { param ->
            trendingRepo.fetch(TrendingRepoParamModel(1))
                .listMerge(
                    {
                        ofType<Either.Right<List<MovieRepoModel>>>()
                            .map {
                                HomeResult.TrendingMovies(it.b.map { movie ->
                                    movieMapper.map(movie)
                                })
                            }
                    },
                    {
                        ofType<Either.Left<ErrorHolder>>()
                            .flatMapLatest {
                                delayedFlow(
                                    HomeResult.Error(it.a.toErrorState()),
                                    HomeResult.LastStable
                                )
                            }
                    }
                )
                .onStart { emit(HomeResult.Loading) }
        }
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val nextPageProcessor: Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.NextPage>().map {
            PageModel.NextPage.Next
        }
            .let(latestMovieProcessor)
    }

    private val latestMovieProcessor: Flow<PageModel>.() -> Flow<HomeResult> = {
        flatMapMerge { param ->
            latestRepo.receiveAndUpdate(param)
                .listMerge(
                    {
                        ofType<Either.Right<List<MovieRepoModel>>>()
                            .map {
                                HomeResult.LatestMovies(
                                    it.b.map { movie ->
                                        movieMapper.map(movie)
                                    }
                                )
                            }
                    },
                    {
                        ofType<Either.Left<ErrorHolder>>()
                            .flatMapLatest {
                                delayedFlow(
                                    HomeResult.Error(it.a.toErrorState()),
                                    HomeResult.LastStable
                                )
                            }
                    }
                )
                .onStart { emit(HomeResult.Loading) }
        }
    }

    private val latestClickProcessor:
            Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.LatestMovieClicked>()
            .map {
                MovieDetailLocalModel(
                    it.movie.id,
                    it.movie.poster,
                    it.movie.cover,
                    it.movie.title,
                    "",
                    it.movie.releaseDate,
                    it.posterTransName,
                    it.releaseTransName,
                    it.titleTransName
                )
            }
            .map {
                Screens.MovieDetail(
                    it,
                    NavigationAnimation.ArcFadeMove(
                        it.posterTransName,
                        it.titleTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.posterTransName,
                        it.titleTransName
                    )
                )
            }
            .navigateTo(navigator)
    }

    private val sliderClicked:
            Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.SliderClicked>()
            .flatMapLatest { sliderClick ->
                trendingRepo.getCache()
                    .map {
                        it.first { movie ->
                            movie.id == sliderClick.movieId
                        }
                    }
                    .map {
                        MovieDetailLocalModel(
                            it.id,
                            it.posterPath,
                            it.backdropPath,
                            it.title,
                            "",
                            it.releaseDate,
                            coverTransName = sliderClick.imgTransName
                        )
                    }
                    .map {
                        Screens.MovieDetail(
                            it,
                            NavigationAnimation.ArcFadeMove(
                                it.posterTransName,
                                it.titleTransName
                            ),
                            NavigationAnimation.ArcFadeMove(
                                it.posterTransName,
                                it.titleTransName
                            )
                        )
                    }
            }.navigateTo(navigator)
    }

    private val searchClicked:
            Flow<HomeIntent>.() -> Flow<HomeResult> = {
        ofType<HomeIntent.SearchClicks>()
            .map {
                Screens.Search(
                    SearchLocalModel(
                        it.backTransName,
                        it.textTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.backTransName,
                        it.textTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.backTransName,
                        it.textTransName
                    )
                )
            }
            .navigateTo(navigator)
//            .onEach {
//                navigator.goTo(it)
//            }
//            .dropWhile { true }
//            .map {
//                HomeResult.LastStable
//            }
    }
}