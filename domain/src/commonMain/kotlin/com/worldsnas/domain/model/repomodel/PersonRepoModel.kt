package com.worldsnas.domain.model.repomodel

data class PersonRepoModel(
    val id: Long = 0,
    val popularity: Double = 0.0,
    val profilePath: String = "",
    val name: String = "",
    val adult: Boolean = false
)