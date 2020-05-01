package com.worldsnas.domain.model.repomodel

data class CrewRepoModel(
    val id: Long = 0,
    val creditID: String = "",
    val department: String = "",
    val gender: Int = 0,
    val job: String = "",
    val name: String = "",
    val profilePath: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val video: Boolean = false,
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val title: String = "",
    val adult: Boolean = false,
    val backdropPath: String = "",
    val posterPath: String = "",
    val genres: List<GenreRepoModel> = emptyList()
)