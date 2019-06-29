package com.worldsnas.domain.repo.moviedetail.model

class MovieDetailRequestModel(
    val movieId : Long,
    val appendToResponse : String = "videos,images,reviews,similar,recommendations,credits,translations,external_ids"
)