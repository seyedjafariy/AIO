package com.worldsnas.moviedetail

import com.worldsnas.core.*
import com.worldsnas.core.mvi.BaseProcessor
import com.worldsnas.core.mvi.toErrorState
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.GalleryImageType
import com.worldsnas.navigation.model.GalleryLocalModel
import com.worldsnas.navigation.model.MovieDetailLocalModel
import kotlinx.coroutines.flow.*

class MovieDetailProcessor(
    repo: MovieDetailRepo,
    navigator: Navigator,
    genreMapper: Mapper<GenreRepoModel, GenreUIModel>,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>
) : BaseProcessor<MovieDetailIntent, MovieDetailResult>() {

    override fun Flow<MovieDetailIntent>.transformers(): List<Flow<MovieDetailResult>> = listOf(
        ofType<MovieDetailIntent.Initial>().let(initialProcessor),
        ofType<MovieDetailIntent.CoverClicked>().let(coverClickedProcessor),
        ofType<MovieDetailIntent.PosterClicked>().let(posterClickedProcessor),
        ofType<MovieDetailIntent.RecommendationClicked>().let(recommendationClickProcessor)
    )

    private val initialProcessor: Flow<MovieDetailIntent>.() -> Flow<MovieDetailResult> = {
        ofType<MovieDetailIntent.Initial>().flatMapLatest { intent ->
            repo.getMovieDetail(MovieDetailRepoParamModel(intent.movie.movieID))
                .listMerge(
                    {
                        ofType<Either.Left<ErrorHolder>>()
                            .flatMapLatest { error ->
                                delayedFlow(
                                    MovieDetailResult.Error(
                                        error.a.toErrorState()
                                    ),
                                    MovieDetailResult.LastStable
                                )
                            }
                    },
                    {
                        ofType<Either.Right<MovieRepoModel>>()
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
                    }
                )
                .onStart {
                    emit(
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
    }

    private val posterClickedProcessor: Flow<MovieDetailIntent.PosterClicked>.() -> Flow<MovieDetailResult> =
        {
            flatMapConcat { intent ->
                repo.getCached()
                    .filter { it != null }
                    .map {
                        it!!
                    }
                    .map { cached ->
                        intent to cached
                    }
            }
                .map { movie ->
                    movie.first to GalleryLocalModel(
                        movie.second.posters.map {
                            it.filePath
                        },
                        0,
                        GalleryImageType.POSTER
                    )
                }
                .onEach {
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
                }
                .dropWhile {
                    true
                }
                .map {
                    MovieDetailResult.LastStable
                }
        }

    private val recommendationClickProcessor: Flow<MovieDetailIntent.RecommendationClicked>.() -> Flow<MovieDetailResult> =
        {
            map {
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
                .onEach {
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
                .dropWhile {
                    true
                }
                .map {
                    MovieDetailResult.LastStable
                }
        }

    private val coverClickedProcessor: Flow<MovieDetailIntent.CoverClicked>.() -> Flow<MovieDetailResult> =
        {
            flatMapLatest { intent ->
                repo.getCached()
                    .filter { it != null }
                    .map { cached ->
                        cached!!
                    }
                    .zipPair(intent)
            }
                .map { pair ->
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
                }
                .onEach {
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
                }
                .dropWhile {
                    true
                }
                .map {
                    MovieDetailResult.LastStable
                }
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