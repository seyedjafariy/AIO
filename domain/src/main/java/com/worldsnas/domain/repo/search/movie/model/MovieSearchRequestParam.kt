package com.worldsnas.domain.repo.search.movie.model

data class MovieSearchRequestParam (
    val query : String,
    val page : Int,
    val includeAdult : Boolean = false
)