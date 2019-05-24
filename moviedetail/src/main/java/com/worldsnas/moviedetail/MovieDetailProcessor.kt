package com.worldsnas.moviedetail

import com.worldsnas.base.toErrorState
import com.worldsnas.core.delayEvent
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.GalleryImageType
import com.worldsnas.navigation.model.GalleryLocalModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.zipWith
import javax.inject.Inject

class MovieDetailProcessor @Inject constructor(
    repo: MovieDetailRepo,
    navigator: Navigator
) : MviProcessor<MovieDetailIntent, MovieDetailResult> {
    override val actionProcessor =
        ObservableTransformer<MovieDetailIntent, MovieDetailResult> { intents ->
            intents.publish {
                Observable.merge(
                    intents
                        .ofType<MovieDetailIntent.Initial>()
                        .compose(initialProcessor),
                    intents
                        .ofType<MovieDetailIntent.CoverClicked>()
                        .compose(coverClickProcessor),
                    intents
                        .ofType<MovieDetailIntent.PosterClicked>()
                        .compose(posterClickProcessor)
                ).observeOn(AndroidSchedulers.mainThread())
            }
        }

    private val initialProcessor =
        ObservableTransformer<MovieDetailIntent.Initial, MovieDetailResult> { intents ->
            intents.switchMap { intent ->
                repo.getMovieDetail(MovieDetailRepoParamModel(intent.movie.movieID))
                    .toObservable()
                    .publish { publish ->
                        Observable.merge(
                            publish
                                .ofType<MovieDetailRepoOutPutModel.Error>()
                                .switchMap { error ->
                                    delayEvent(
                                        MovieDetailResult.Error(
                                            error.err.toErrorState()
                                        ),
                                        MovieDetailResult.LastStable
                                    )
                                },
                            publish
                                .ofType<MovieDetailRepoOutPutModel.Success>()
                                .map {
                                    MovieDetailResult.Detail(
                                        it.movie.title,
                                        it.movie.posterPath,
                                        getTime(it.movie.runtime),
                                        it.movie.releaseDate,
                                        it.movie.overview,
                                        it.movie.backdrops.map { back -> back.filePath }
                                    )
                                }
                        )
                    }
                    .startWith(
                        MovieDetailResult.Detail(
                            intent.movie.title,
                            intent.movie.poster,
                            "",
                            intent.movie.releasedDate,
                            intent.movie.description,
                            emptyList()
                        )
                    )
            }
        }


    private val posterClickProcessor =
        ObservableTransformer<MovieDetailIntent.PosterClicked, MovieDetailResult> { intents ->
            intents.switchMap { intent ->
                repo.getCached()
                    .toObservable()
                    .ofType<MovieDetailRepoOutPutModel.Cached>()
                    .filter { it.movie != null }
                    .map { cached ->
                        cached.movie!!
                    }
            }.map { movie ->
                GalleryLocalModel(
                    movie.posters.map {
                        it.filePath
                    },
                    0,
                    GalleryImageType.POSTER
                )
            }.compose(galleryProcessor)
        }

    private val coverClickProcessor =
        ObservableTransformer<MovieDetailIntent.CoverClicked, MovieDetailResult> { intents ->
            intents.switchMap { intent ->
                repo.getCached()
                    .toObservable()
                    .ofType<MovieDetailRepoOutPutModel.Cached>()
                    .filter { it.movie != null }
                    .map { cached ->
                        cached.movie!!
                    }
                    .zipWith(Observable.just(intent.coverUrl))
            }.map { pair ->
                var index = pair.first.backdrops.indexOfFirst {
                    it.filePath == pair.second
                }
                if (index == -1) {
                    index = 0
                }

                GalleryLocalModel(
                    pair.first.backdrops.map {
                        it.filePath
                    },
                    index,
                    GalleryImageType.COVER
                )
            }.compose(galleryProcessor)
        }

    private val galleryProcessor =
        ObservableTransformer<GalleryLocalModel, MovieDetailResult> { localModels ->
            localModels
                .doOnNext {
                    navigator.goTo(
                        Screens.Gallery(
                            it
                        )
                    )
                }
                .ignoreElements()
                .toObservable()
        }

    private fun getTime(runtime: Int): String =
        "${runtime / 60}:${runtime % 60}"
}