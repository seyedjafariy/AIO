package com.worldsnas.domain.repomodel

data class TranslationRepoModel(
    val iso_3166_1: String = "",
    val iso_639_1: String = "",
    val name: String = "",
    val englishName: String = "",
    val title: String = "",
    val overview: String = "",
    val homePage: String = ""
)