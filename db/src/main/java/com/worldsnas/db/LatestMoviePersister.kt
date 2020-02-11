package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import kotlinx.coroutines.flow.Flow

interface LatestMoviePersister {
    fun observeMovies(): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun movieCount(): Flow<Long>
}

class LatestMoviePersisterImpl (
    private val queries: LatestMovieQueries
): LatestMoviePersister{
    override fun observeMovies(): Flow<List<Movie>> =
        queries
            .getLatestMovies()
            .asFlow()
            .mapToList()

    override suspend fun clearMovies() =
        queries.clearLatestMovies()

    override suspend fun insertMovie(movie: Movie) =
        queries.insertMovie(
            movie.id,
            movie.title,
            movie.backdropImage,
            movie.posterImage,
            movie.release_date,
            true
        )

    override suspend fun insertMovies(movies: List<Movie>) =
        movies.forEach {
            insertMovie(it)
        }

    override fun movieCount(): Flow<Long> =
        queries
            .latestMovieCount()
            .asFlow()
            .mapToOneOrDefault(0)
}