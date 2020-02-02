package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrDefault
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MoviePersisterImpl constructor(
    private val queries: MovieQueries
) : MoviePersister {

    override fun observeMovies(): Flow<List<Movie>> =
        queries.getMovies()
            .asFlow()
            .mapToList()

    override suspend fun clearMovies() {
        queries.clearMovies()
    }

    override suspend fun insertMovie(movie: Movie) : Unit =
        queries.insertMovie(
            movie.id,
            movie.title,
            movie.backdropImage,
            movie.posterImage,
            movie.release_date
        )

    override suspend fun insertMovies(movies: List<Movie>) : Unit =
        movies.forEach {
            insertMovie(it)
        }


    override fun getMovie(id: Long) =
        queries.getMovie(id)
            .asFlow()
            .mapToOneNotNull()

    override fun getMovies(fromId: Long, count: Int): Flow<List<Movie>> = flow {

    }

    override fun movieCount(): Flow<Long> =
        queries.movieCount()
            .asFlow()
            .mapToOneOrDefault(0)

}