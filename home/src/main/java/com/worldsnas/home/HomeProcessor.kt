package com.worldsnas.home

import com.worldsnas.base.toErrorState
import com.worldsnas.core.delayEvent
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoOutputModel
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repomodel.MovieRepoModel
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.panther.Mapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import javax.inject.Inject

@FeatureScope
class HomeProcessor @Inject constructor(
    latestRepo: LatestMovieRepo,
    trendingRepo: TrendingRepo,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>
) : MviProcessor<HomeIntent, HomeResult> {

    private var latest: List<MovieRepoModel> = emptyList()
    private var trending: List<MovieRepoModel> = emptyList()

    override val actionProcessor = ObservableTransformer<HomeIntent, HomeResult> {
        it.publish { publish ->
            Observable.merge(
                publish.ofType<HomeIntent.Initial>().compose(latestProcessor),
                publish.ofType<HomeIntent.Initial>().compose(trendingProcessor)
            )
        }.observeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val latestProcessor = ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
        actions.switchMap { intent ->
            latestRepo.observerAndUpdate()
                .doOnNext {
                    if (it is LatestMovieRepoOutputModel.Success) {
                        latest = it.movies
                    }
                }
                .switchMap { repoModel ->
                    when (repoModel) {
                        is LatestMovieRepoOutputModel.Success ->
                            Observable.just(HomeResult.LatestMovies(
                                repoModel.movies.map{movie->
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

    @Suppress("UNUSED_ANONYMOUS_PARAMETER")
    private val trendingProcessor = ObservableTransformer<HomeIntent.Initial, HomeResult> { actions ->
        actions.switchMap { intent ->
            trendingRepo.observerAndUpdate()
                .doOnNext {
                    if (it is TrendingRepoOutputModel.Success) {
                        trending = it.movies
                    }
                }
                .switchMap { repoModel ->
                    when (repoModel) {
                        is TrendingRepoOutputModel.Success ->
                            Observable.just(HomeResult.TrendingMovies(
                                repoModel.movies.map {movie->
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
}