package com.worldsnas.db

import kotlinx.coroutines.flow.Flow

interface MoviePersister {
    fun getMovies(): Flow<List<Movie>>
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovie(id: Long): Flow<Movie>
}