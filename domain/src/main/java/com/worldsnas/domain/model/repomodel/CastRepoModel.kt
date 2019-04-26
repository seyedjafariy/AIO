package com.worldsnas.domain.model.repomodel


data class CastRepoModel (
    val id: Long = 0,
    val castID: Int = 0,
    val character: String = "",
    val creditId: String = "",
    val gender: Int = 0,
    val name: String = "",
    val order: Int = 0,
    val profilePath: String = "",
    val posterPath: String = "",
    val adult: Boolean = false,
    val backdropPath: String = "",
    val voteCount: Int = 0,
    val video: Boolean = false,
    val popularity: Double = 0.0,
    val originalLanguage: String = "",
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = "",
    val genreIds: List<GenreRepoModel> = emptyList()
)