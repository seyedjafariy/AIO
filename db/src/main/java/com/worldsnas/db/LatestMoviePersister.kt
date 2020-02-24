package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import com.worldsnas.core.mergeIterable
import com.worldsnas.core.toListFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface LatestMoviePersister {
    fun observeSimpleMovies(): Flow<List<Movie>>
    fun observeMovies(): Flow<List<MovieGenre>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun movieCount(): Flow<Long>
    fun getMovie(id: Long): Flow<Movie?>
    fun findAny(ids: List<Long>): Flow<Movie?>
}

class LatestMoviePersisterImpl(
        private val queries: LatestMovieQueries,
        private val genreQueries: GenreQueries
) : LatestMoviePersister {
    override fun observeSimpleMovies(): Flow<List<Movie>> =
            queries
                    .getLatestMovies()
                    .asFlow()
                    .mapToList(Dispatchers.IO)

    override fun observeMovies(): Flow<List<MovieGenre>> =
            observeSimpleMovies()
                    .movieGenres()

    private fun Flow<List<Movie>>.movieGenres(): Flow<List<MovieGenre>> = flatMapMerge { movies ->
        movies.asFlow()
                .flatMapConcat { movie ->
                    genreQueries.getMovieGenres(movie.id) { id, title, updatedAt ->
                        Genre.Impl(
                                id!!, title!!, updatedAt!!
                        )
                    }
                            .asFlow()
                            .mapToList(Dispatchers.IO)
                            .take(1)
                            .map { genres ->
                                MovieGenre(
                                        movie,
                                        genres
                                )
                            }
                }
                .toListFlow()
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
