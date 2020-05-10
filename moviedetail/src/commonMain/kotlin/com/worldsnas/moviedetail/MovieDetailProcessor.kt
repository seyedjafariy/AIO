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
import com.worldsnas.navigation.navigateTo
import kotlinx.coroutines.flow.*

class MovieDetailProcessor(
    repo: MovieDetailRepo,
    navigator: Navigator,
    genreMapper: Mapper<GenreRepoModel, GenreUIModel>,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>
) : BaseProcessor<MovieDetailIntent, MovieDetailResult>() {

    override fun transformers(): List<FlowBlock<MovieDetailIntent, MovieDetailResult>> = listOf(
        initialProcessor,
        coverClickedProcessor,
        posterClickedProcessor,
        recommendationClickProcessor
    )

    private val initialProcessor: Flow<MovieDetailIntent>.() -> Flow<MovieDetailResult> = {
        ofType<MovieDetailIntent.Initial>()
            .flatMapLatest { intent ->
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

    private val posterClickedProcessor: Flow<MovieDetailIntent>.() -> Flow<MovieDetailResult> =
        {
            ofType<MovieDetailIntent.PosterClicked>()
                .flatMapConcat { intent ->
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
                .map {
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
                }
                .navigateTo(navigator)
        }

    private val recommendationClickProcessor: Flow<MovieDetailIntent>.() -> Flow<MovieDetailResult> =
        {
            ofType<MovieDetailIntent.RecommendationClicked>()
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

    private val coverClickedProcessor: Flow<MovieDetailIntent>.() -> Flow<MovieDetailResult> =
        {
            ofType<MovieDetailIntent.CoverClicked>()
                .flatMapLatest { intent ->
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
                .map {
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
                }
                .navigateTo(navigator)
        }

    private fun getTime(runtime: Int): String =
        "${runtime / 60}:${runtime % 60}"
}