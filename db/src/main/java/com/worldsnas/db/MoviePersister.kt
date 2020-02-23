package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import kotlinx.coroutines.flow.Flow

interface MoviePersister {

    fun observeMovies(isLatest: Boolean = false): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovie(id: Long): Flow<Movie>
    fun movieCount(isLatest: Boolean = false): Flow<Long>
}

class MoviePersisterImpl constructor(
        private val queries: MovieQueries
) : MoviePersister {

    override fun observeMovies(isLatest: Boolean): Flow<List<Movie>> =
            queries.getMovies()
                    .asFlow()
                    .mapToList()

    override suspend fun clearMovies() {
        queries.clearMovies()
    }

    override suspend fun insertMovie(movie: Movie): Unit =
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
                    movie.voteCount
            )

    override suspend fun insertMovies(movies: List<Movie>): Unit =
            movies.forEach {
                insertMovie(it)
            }

    override fun getMovie(id: Long) =
            queries.getMovie(id)
                    .asFlow()
                    .mapToOneNotNull()

    override fun movieCount(isLatest: Boolean): Flow<Long> =
            queries.movieCount()
                    .asFlow()
                    .mapToOneOrDefault(0)
}

const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SS"