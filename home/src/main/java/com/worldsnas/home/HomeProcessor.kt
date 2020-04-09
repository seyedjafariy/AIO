package com.worldsnas.home

import arrow.core.Either
import com.worldsnas.base.toErrorState
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.delayEvent
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.panther.Mapper
import com.worldsnas.view_component.Movie
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import kotlinx.coroutines.rx2.asObservable
import javax.inject.Inject

@FeatureScope
class HomeProcessor @Inject constructor(
    private val navigator: Navigator,
    latestRepo: LatestMovieRepo,
    trendingRepo: TrendingRepo,
    movieMapper: Mapper<MovieRepoModel, Movie>
) : MviProcessor<HomeIntent, HomeResult> {

    override val actionProcessor = ObservableTransformer<HomeIntent, HomeResult> {
        it.publish { publish ->
            Observable.mergeArray(
                publish.ofType<HomeIntent.Initial>().compose(latestProcessor),
                publish.ofType<HomeIntent.Initial>().compose(trendingProcessor),
                publish.ofType<HomeIntent.NextPage>().compose(nextPageProcessor),
                publish.ofType<HomeIntent.LatestMovieClicked>().compose(latestClickProcessor),
                publish.ofType<HomeIntent.SliderClicked>().compose(sliderClicked),
                publish.ofType<HomeIntent.SearchClicks>().compose(searchClicked)
            )
        }.observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val latestProcessor = ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
        actions
            .map { PageModel.First }
            .compose(latestMovieProcessor)
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val trendingProcessor =
        ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
            actions.switchMap { intent ->
                trendingRepo.fetch(TrendingRepoParamModel(1))
                    .toObservable()
                    .publish { publish ->
                        Observable.merge(
                            publish.ofType<TrendingRepoOutputModel.Success>()
                                .map {
                                    HomeResult.TrendingMovies(it.allPages.map { movie ->
                                        movieMapper.map(movie)
                                    })
                                },
                            publish.ofType<TrendingRepoOutputModel.Error>()
                                .switchMap {
                                    delayEvent(
                                        HomeResult.Error(it.err.toErrorState()),
                                        HomeResult.LastStable
                                    )
                                }
                        )
                    }
                    .startWith(HomeResult.Loading)
            }
        }

    private val nextPageProcessor =
        ObservableTransformer<HomeIntent.NextPage, HomeResult> { actions ->
            actions
                .map {
                    PageModel.NextPage.Next
                }
                .compose(latestMovieProcessor)
        }

    private val latestMovieProcessor =
        ObservableTransformer<PageModel, HomeResult> { actions ->
            actions
                .switchMap { param ->
                    latestRepo.receiveAndUpdate(param)
                        .asObservable()
                        .publish { publish ->
                            Observable.merge(
                                publish.ofType<Either.Right<List<MovieRepoModel>>>()
                                    .map {
                                        HomeResult.LatestMovies(
                                            it.b.map { movie ->
                                                movieMapper.map(movie)
                                            }
                                        )
                                    },
                                publish.ofType<Either.Left<ErrorHolder>>()
                                    .switchMap {
                                        delayEvent(
                                            HomeResult.Error(it.a.toErrorState()),
                                            HomeResult.LastStable
                                        )
                                    }
                            )
                        }
                        .startWith(HomeResult.Loading)
                }
        }

    private val latestClickProcessor =
        ObservableTransformer<HomeIntent.LatestMovieClicked, HomeResult> { actions ->
            actions
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
                .doOnNext {
                    navigator.goTo(
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
                    )
                }
                .ignoreElements()
                .toObservable()
        }

    private val sliderClicked =
        ObservableTransformer<HomeIntent.SliderClicked, HomeResult> { actions ->
            actions.switchMap { click ->
                trendingRepo.getCache()
                    .toObservable()
                    .map {
                        it.allPages.first { movie ->
                            movie.id == click.movieId
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
                            coverTransName = click.imgTransName
                        )
                    }
                    .doOnNext {
                        navigator.goTo(
                            Screens.MovieDetail(
                                it,
                                NavigationAnimation.ArcFadeMove(
                                    click.imgTransName
                                ),
                                NavigationAnimation.ArcFadeMove(
                                    click.imgTransName
                                )
                            )
                        )
                    }
                    .ignoreElements()
                    .toObservable<HomeResult>()
            }
        }

    private val searchClicked =
        ObservableTransformer<HomeIntent.SearchClicks, HomeResult> { actions ->
            actions.map {
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
                .doOnNext {
                    navigator.goTo(it)
                }
                .ignoreElements()
                .toObservable()
        }


}