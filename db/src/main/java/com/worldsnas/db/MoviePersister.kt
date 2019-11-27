package com.worldsnas.db

import kotlinx.coroutines.flow.Flow

interface MoviePersister {

    fun observeMovies(): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovie(id: Long): Flow<Movie>
}