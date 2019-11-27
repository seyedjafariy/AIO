package com.worldsnas.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneNotNull
import kotlinx.coroutines.flow.Flow

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
            movie.posterImage
        )

    override suspend fun insertMovies(movies: List<Movie>) : Unit =
        movies.forEach {
            insertMovie(it)
        }


    override fun getMovie(id: Long) =
        queries.getMovie(id)
            .asFlow()
            .mapToOneNotNull()
}