package com.worldsnas.domain.model.repomodel

data class TranslationRepoModel(
    val id: Long = 0,
    val iso_3166_1: String = "",
    val iso_639_1: String = "",
    val name: String = "",
    val englishName: String = "",
    val title: String = "",
    val overview: String = "",
    val homePage: String = ""
)