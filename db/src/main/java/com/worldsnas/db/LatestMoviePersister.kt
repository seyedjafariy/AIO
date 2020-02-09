package com.worldsnas.db

import kotlinx.coroutines.flow.Flow

interface LatestMoviePersister {
    fun observeMovies(): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovie(id: Long): Flow<Movie>
    fun movieCount(): Flow<Long>
}

class LatestMoviePersisterImpl (
    private val moviePersister: MoviePersister
): LatestMoviePersister{
    override fun observeMovies(): Flow<List<Movie>> =
        moviePersister.observeMovies(true)

    override suspend fun clearMovies() =
        moviePersister.clearMovies()

    override suspend fun insertMovie(movie: Movie) =
        moviePersister.insertMovie(movie.castToImpl().copy(is_latest = true))

    override suspend fun insertMovies(movies: List<Movie>) =
        movies.forEach {
            insertMovie(it)
        }

    override fun getMovie(id: Long): Flow<Movie> =
        moviePersister.getMovie(id)

    override fun movieCount(): Flow<Long> =
        moviePersister.movieCount()
}