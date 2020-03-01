package com.worldsnas.db

class MovieGenre(
        val movie: Movie,
        val genres : List<Genre>
)

data class CompleteMovie(
        val movie : Movie,
        val genres : List<Genre> = emptyList(),
        val similars : List<Movie> = emptyList(),
        val recommended : List<Movie> = emptyList()
)