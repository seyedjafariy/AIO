package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import com.worldsnas.core.listMerge
import com.worldsnas.core.mergeIterable
import com.worldsnas.core.toListFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface LatestMoviePersister {
    fun observeSimpleMovies(): Flow<List<Movie>>
    fun observeMovies(): Flow<List<CompleteMovie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun movieCount(): Flow<Long>
    fun getMovie(id: Long): Flow<Movie?>
    fun findAny(ids: List<Long>): Flow<Movie?>
}

typealias MovieToComplete = (CompleteMovie) -> Flow<CompleteMovie>

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

    override fun observeMovies(): Flow<List<CompleteMovie>> =
            observeSimpleMovies()
                    .addToMovie(listOf(
                            ::movieGenres,
                            ::movieSimilars
                    ))


    private fun Flow<List<Movie>>.addToMovie(calls: List<MovieToComplete>): Flow<List<CompleteMovie>> =
            flatMapMerge { movies ->
                val completeFlow = movies.asFlow()
                        .map {
                            CompleteMovie(it)
                        }

                calls.forEach { call ->
                    completeFlow.flatMapConcat{
                        call(it)
                    }
                }

                completeFlow.toListFlow()
            }

    private fun movieGenres(completeMovie: CompleteMovie): Flow<CompleteMovie> =
            genreQueries.getMovieGenres(completeMovie.movie.id) { id, title, updatedAt ->
                Genre.Impl(
                        id!!, title!!, updatedAt!!
                )
            }
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .take(1)
                    .map { genres ->
                        completeMovie.copy(genres = genres)
                    }

    private fun movieSimilars(completeMovie: CompleteMovie): Flow<CompleteMovie> =
            movieQueries.getSimilarMovies(completeMovie.movie.id)
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .take(1)
                    .map { similars ->
                        completeMovie.copy(similars = similars)
                    }


    override suspend fun clearMovies() =
            queries.clearLatestMovies()

    override suspend fun insertMovie(movie: Movie) =
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
                    true
            )

    override suspend fun insertMovies(movies: List<Movie>) =
            movies.forEach {
                insertMovie(it)
            }

    override fun movieCount(): Flow<Long> =
            flow {
                emit(
                        queries
                                .latestMovieCount()
                                .executeAsOne()
                )
            }

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
