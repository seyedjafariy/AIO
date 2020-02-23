package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface LatestMoviePersister {
    fun observeMovies(): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun movieCount(): Flow<Long>
    fun getMovie(id: Long): Flow<Movie?>
    fun findAny(ids: List<Long>): Flow<Movie?>
}

class LatestMoviePersisterImpl(
    private val queries: LatestMovieQueries
) : LatestMoviePersister {
    override fun observeMovies(): Flow<List<Movie>> =
        queries
            .getLatestMovies()
            .asFlow()
            .mapToList(Dispatchers.IO)

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

fun <T> Flow<T>.ifEmptyEmit(item: T): Flow<T> =
    flow {
        var emitted = false
        collect { value ->
            emitted = true
            emit(value)
        }

        if (!emitted) {
            emit(item)
        }
    }