package com.worldsnas.domain.model.repomodel

data class VideoRepoModel(
    val id: Long = 0,
    val videoId: String = "",
    val iso_639_1: String = "",
    val iso_3166_1: String = "",
    val key: String = "",
    val name: String = "",
    val site: String = "",
    val size: Int = 0,
    val type: String = ""
)