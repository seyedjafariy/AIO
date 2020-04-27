package com.worldsnas.moviedetail

import arrow.core.Either
import com.worldsnas.base.toErrorState
import com.worldsnas.core.ErrorHolder
import com.worldsnas.base.delayEvent
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.GalleryImageType
import com.worldsnas.navigation.model.GalleryLocalModel
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.panther.Mapper
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.ofType
import io.reactivex.rxkotlin.zipWith
import kotlinx.coroutines.rx2.asObservable
import javax.inject.Inject

class MovieDetailProcessor @Inject constructor(
    repo: MovieDetailRepo,
    navigator: Navigator,
    genreMapper: Mapper<GenreRepoModel, GenreUIModel>,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>
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
                        .compose(posterClickProcessor),
                    intents
                        .ofType<MovieDetailIntent.RecommendationClicked>()
                        .compose(recommendationClickProcessor)
                ).observeOn(AndroidSchedulers.mainThread())
            }
        }

    private val initialProcessor =
        ObservableTransformer<MovieDetailIntent.Initial, MovieDetailResult> { intents ->
            intents.switchMap { intent ->
                repo.getMovieDetail(MovieDetailRepoParamModel(intent.movie.movieID))
                    .asObservable()
                    .publish { publish ->
                        Observable.merge(
                            publish
                                .ofType<Either.Left<ErrorHolder>>()
                                .switchMap { error ->
                                    delayEvent(
                                        MovieDetailResult.Error(
                                            error.a.toErrorState()
                                        ),
                                        MovieDetailResult.LastStable
                                    )
                                },
                            publish
                                .ofType<Either.Right<MovieRepoModel>>()
                                .map {
                                    with(it.b) {
                                        MovieDetailResult.Detail(
                                            title,
                                            posterPath,
                                            getTime(runtime),
                                            releaseDate,
                                            overview,
                                            backdrops.map { back -> back.filePath },
                                            genres.map { genre -> genreMapper.map(genre) },
                                            recommendations.map { movie ->
                                                movieMapper.map(
                                                    movie
                                                )
                                            },
                                            similar.map { movie -> movieMapper.map(movie) })
                                    }
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
                            emptyList(),
                            emptyList(),
                            emptyList(),
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
                        intent to cached.movie!!
                    }
            }.map { movie ->
                movie.first to GalleryLocalModel(
                    movie.second.posters.map {
                        it.filePath
                    },
                    0,
                    GalleryImageType.POSTER
                )
            }.doOnNext {
                navigator.goTo(
                    Screens.Gallery(
                        it.second,
                        NavigationAnimation.CircularReveal(
                            it.first.cx,
                            it.first.cy,
                            200
                        ),
                        NavigationAnimation.CircularReveal(
                            it.first.cx,
                            it.first.cy,
                            200
                        )
                    )
                )
            }.ignoreElements()
                .toObservable()
        }

    private val recommendationClickProcessor =
        ObservableTransformer<MovieDetailIntent.RecommendationClicked, MovieDetailResult> { intents ->
            intents.map {
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
            }.doOnNext {
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
            }.ignoreElements()
                .toObservable()
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
                    .zipWith(Observable.just(intent))
            }.map { pair ->
                var index = pair.first.backdrops.indexOfFirst {
                    it.filePath == pair.second.coverUrl
                }
                if (index == -1) {
                    index = 0
                }

                pair.second to GalleryLocalModel(
                    pair.first.backdrops.map {
                        it.filePath
                    },
                    index,
                    GalleryImageType.COVER
                )
            }.doOnNext {
                navigator.goTo(
                    Screens.Gallery(
                        it.second,
                        NavigationAnimation.CircularReveal(
                            it.first.cx,
                            it.first.cy,
                            200
                        ),
                        NavigationAnimation.CircularReveal(
                            it.first.cx,
                            it.first.cy,
                            200
                        )
                    )
                )
            }.ignoreElements()
                .toObservable()
        }

//    private val galleryProcessor =
//        ObservableTransformer<GalleryLocalModel, MovieDetailResult> { localModels ->
//            localModels
//                .doOnNext {
//                    navigator.goTo(
//                        Screens.Gallery(
//                            it
//                        )
//                    )
//                }
//                .ignoreElements()
//                .toObservable()
//        }

    private fun getTime(runtime: Int): String =
        "${runtime / 60}:${runtime % 60}"
}