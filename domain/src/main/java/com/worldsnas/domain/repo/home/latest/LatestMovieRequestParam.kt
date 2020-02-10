package com.worldsnas.domain.repo.home.latest

import java.util.*

data class LatestMovieRequestParam(
    val date : Date,
    val page : Int
)