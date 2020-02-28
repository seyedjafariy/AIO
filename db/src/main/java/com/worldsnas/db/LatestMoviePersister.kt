package com.worldsnas.db

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface LatestMoviePersister {
    fun observeSimpleMovies(): Flow<List<Movie>>
    fun observeMovies(
        genres: Boolean = false,
        similars: Boolean = false,
        recomendations: Boolean = false
    ): Flow<List<CompleteMovie>>

    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie, isLatest: Boolean = true)
    suspend fun insertMovies(movies: List<CompleteMovie>)
    fun movieCount(): Flow<Long>
    fun getMovie(id: Long): Flow<Movie?>
    fun findAny(ids: List<Long>): Flow<Movie?>
}

typealias MovieCompletor = (List<CompleteMovie>) -> Flow<List<CompleteMovie>>

class LatestMoviePersisterImpl(
    private val queries: LatestMovieQueries,
    private val genreQueries: GenreQueries,
    private val movieQueries: MovieQueries
) : LatestMoviePersister {
    override fun observeSimpleMovies(): Flow<List<Movie>> =
        queries
            .getLatestMovies()
            .asFlow()
            .mapToList(Dispatchers.IO)

    private fun Flow<List<Movie>>.addToMovie(vararg calls: MovieCompletor?): Flow<List<CompleteMovie>> =
        map { movies ->
            movies.map { CompleteMovie(it) }
        }
            .apply {
                calls
                    .filterNotNull()
                    .forEach { call ->
                        this.flatMapMerge {
                            call(it)
                        }
                    }
            }

    private fun <T : Any> Query<T>.addChildrenToComplete(
        completeMovies: List<CompleteMovie>,
        transform: suspend (List<T>, CompleteMovie) -> CompleteMovie
    ): Flow<List<CompleteMovie>> =
        asFlow()
            .mapToList(Dispatchers.IO)
            .take(1)
            .combine(flowOf(completeMovies)) { items, completes ->
                completes.map { complete ->
                    transform(items, complete)
                }
            }

    override fun observeMovies(
        genres: Boolean,
        similars: Boolean,
        recomendations: Boolean
    ): Flow<List<CompleteMovie>> =
        observeSimpleMovies()
            .addToMovie(
                ::movieGenres.takeIf { genres },
                ::movieSimilars.takeIf { similars },
                ::movieRecommended.takeIf { recomendations }
            )

    private fun movieGenres(completeMovies: List<CompleteMovie>): Flow<List<CompleteMovie>> =
        genreQueries.getMoviesGenres(completeMovies.map { it.movie.id })
            .addChildrenToComplete(completeMovies) { genres, complete ->
                complete.copy(
                    genres = genres
                        .filter {
                            it.originalMovieID == complete.movie.id
                        }
                        .map { genre ->
                            with(genre) {
                                Genre.Impl(id!!, title!!, updatedAt!!)
                            }
                        }
                )
            }

    private fun movieSimilars(completeMovies: List<CompleteMovie>): Flow<List<CompleteMovie>> =
        movieQueries.getMoviesSimilar(completeMovies.map { it.movie.id })
            .addChildrenToComplete(completeMovies) { similars, complete ->
                complete.copy(similars = similars
                    .filter { similar ->
                        similar.originalMovieID == complete.movie.id
                    }
                    .map { similar ->
                        with(similar) {
                            Movie.Impl(
                                id,
                                title,
                                adult,
                                originalTitle,
                                budget,
                                homePage,
                                imdbId,
                                facebookId,
                                instagramId,
                                twitterId,
                                originalLanguage,
                                overview,
                                popularity,
                                backdropImage,
                                posterImage,
                                releaseDate,
                                revenue,
                                runtime,
                                status,
                                tagLine,
                                video,
                                voteAverage,
                                voteCount,
                                updatedAt,
                                isLatest
                            )
                        }
                    }
                )
            }

    private fun movieRecommended(completeMovies: List<CompleteMovie>): Flow<List<CompleteMovie>> =
        movieQueries.getMoviesRecommended(completeMovies.map { it.movie.id })
            .addChildrenToComplete(completeMovies) { recommendeds, complete ->
                complete.copy(recommended = recommendeds
                    .filter { recommended ->
                        recommended.originalMovieID == complete.movie.id
                    }
                    .map { recommended ->
                        with(recommended) {
                            Movie.Impl(
                                id,
                                title,
                                adult,
                                originalTitle,
                                budget,
                                homePage,
                                imdbId,
                                facebookId,
                                instagramId,
                                twitterId,
                                originalLanguage,
                                overview,
                                popularity,
                                backdropImage,
                                posterImage,
                                releaseDate,
                                revenue,
                                runtime,
                                status,
                                tagLine,
                                video,
                                voteAverage,
                                voteCount,
                                updatedAt,
                                isLatest
                            )
                        }
                    }
                )
            }

    override suspend fun clearMovies() =
        queries.clearLatestMovies()

    override suspend fun insertMovie(movie: Movie, isLatest: Boolean) =
        queries.insertMovie(
            movie.id,
            movie.title,
            movie.adult,
            movie.originalTitle,
            movie.originalLanguage,
            movie.budget,
            movie.homePage,
            movie.imdbId,
            movie.facebookId,
            movie.instagramId,
            movie.twitterId,
            movie.overview,
            movie.popularity,
            movie.backdropImage,
            movie.posterImage,
            movie.releaseDate,
            movie.revenue,
            movie.runtime,
            movie.status,
            movie.tagLine,
            movie.video,
            movie.voteAverage,
            movie.voteCount,
            isLatest
        )

    override suspend fun insertMovies(movies: List<CompleteMovie>) =
        withContext(Dispatchers.IO) {
            queries.transaction {
                movies.forEach { withDetails ->
                    runBlocking {
                        insertMovie(withDetails.movie)

                        withDetails.genres.forEach { genre ->
                            insertGenre(genre)
                            genreQueries.insertMovieGenres(genre.id, withDetails.movie.id)
                        }
                        withDetails.recommended.forEach { recomMovie ->
                            insertMovie(recomMovie, false)
                            movieQueries.insertRecommendedMovie(withDetails.movie.id, recomMovie.id)
                        }
                        withDetails.similars.forEach { similarMovie ->
                            insertMovie(similarMovie, false)
                            movieQueries.insertSimilarMovie(withDetails.movie.id, similarMovie.id)
                        }
                    }
                }
            }
        }

    private suspend fun insertGenre(genre: Genre) =
        genreQueries.insertGenre(genre.id, genre.updatedAt)

    override fun movieCount(): Flow<Long> =
        queries
            .latestMovieCount()
            .asFlow()
            .mapToOne()

    override fun getMovie(id: Long): Flow<Movie?> =
        queries.getLatestMovie(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .take(1)

    override fun findAny(ids: List<Long>): Flow<Movie?> =
        queries.findMovie(ids)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
}
