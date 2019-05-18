package com.worldsnas.home

import com.worldsnas.base.toErrorState
import com.worldsnas.core.delayEvent
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoOutputModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoParamModel
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.panther.Mapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import javax.inject.Inject

@FeatureScope
class HomeProcessor @Inject constructor(
    private val navigator: Navigator,
    latestRepo: LatestMovieRepo,
    trendingRepo: TrendingRepo,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>
) : MviProcessor<HomeIntent, HomeResult> {

    override val actionProcessor = ObservableTransformer<HomeIntent, HomeResult> {
        it.publish { publish ->
            Observable.merge(
                publish.ofType<HomeIntent.Initial>().compose(latestProcessor),
                publish.ofType<HomeIntent.Initial>().compose(trendingProcessor),
                publish.ofType<HomeIntent.NextPage>().compose(nextPageProcessor),
                publish.ofType<HomeIntent.LatestMovieClicked>().compose(latestClickProcessor)
            )
        }.observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val latestProcessor = ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
        actions
            .map { LatestMovieRepoParamModel(1) }
            .compose(latestMovieProcessor)
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val trendingProcessor = ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
        actions.switchMap { intent ->
            trendingRepo.fetch(TrendingRepoParamModel(1))
                .toObservable()
                .switchMap { repoModel ->
                    when (repoModel) {
                        is TrendingRepoOutputModel.Success ->
                            Observable.just(HomeResult.TrendingMovies(
                                repoModel.allPages.map { movie ->
                                    movieMapper.map(movie)
                                }
                            ))
                        is TrendingRepoOutputModel.Error ->
                            delayEvent(
                                HomeResult.Error(repoModel.err.toErrorState()),
                                HomeResult.LastStable
                            )
                    }
                }
                .startWith(HomeResult.Loading)
        }
    }

    private val nextPageProcessor = ObservableTransformer<HomeIntent.NextPage, HomeResult> { actions ->
        actions
            .map { intent ->
                LatestMovieRepoParamModel((intent.totalCount / 20) + 1)
            }
            .filter { it.page > 1 }
            .compose(latestMovieProcessor)
    }

    private val latestMovieProcessor = ObservableTransformer<LatestMovieRepoParamModel, HomeResult> { actions ->
        actions.switchMap { param ->
            latestRepo.fetch(param)
                .toObservable()
                .switchMap { repoModel ->
                    when (repoModel) {
                        is LatestMovieRepoOutputModel.Success ->
                            Observable.just(HomeResult.LatestMovies(
                                repoModel.all.map { movie ->
                                    movieMapper.map(movie)
                                }
                            ))
                        is LatestMovieRepoOutputModel.Error ->
                            delayEvent(
                                HomeResult.Error(repoModel.err.toErrorState()),
                                HomeResult.LastStable
                            )
                    }
                }
                .startWith(HomeResult.Loading)
        }
    }

    private val latestClickProcessor = ObservableTransformer<HomeIntent.LatestMovieClicked, HomeResult> { actions ->
        actions
            .map {
                MovieDetailLocalModel(
                    it.movie.id,
                    it.movie.poster,
                    it.movie.cover,
                    it.movie.title,
                    "",
                    it.movie.releaseDate
                )
            }
            .doOnNext {
                navigator.goTo(Screens.MovieDetail(it))
            }
            .ignoreElements()
            .toObservable()
    }
}