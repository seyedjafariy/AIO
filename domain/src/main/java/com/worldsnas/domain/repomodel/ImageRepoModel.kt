package com.worldsnas.domain.repomodel

data class ImageRepoModel(
    val id: Long = 0,
    val aspectRatio: Double = 0.0,
    val filePath: String = "",
    val height: Int = 0,
    val iso: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val width: Int = 0
)