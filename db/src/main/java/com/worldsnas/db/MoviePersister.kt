package com.worldsnas.db

import kotlinx.coroutines.flow.Flow

interface MoviePersister {

    fun observeMovies(): Flow<List<Movie>>
    suspend fun clearMovies()
    suspend fun insertMovie(movie: Movie)
    suspend fun insertMovies(movies: List<Movie>)
    fun getMovie(id: Long): Flow<Movie>
    fun getMovies(fromId : Long, count : Int) : Flow<List<Movie>>
    fun movieCount() : Flow<Long>
}

const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SS"