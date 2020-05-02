package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageServerModel(
    @SerialName(value = "aspect_ratio")
    val aspectRatio: Double = 0.0,
    @SerialName(value = "file_path")
    val filePath: String = "",
    @SerialName(value = "height")
    val height: Int = 0,
    @SerialName(value = "iso_639_1")
    val iso: String? = "",
    @SerialName(value = "vote_average")
    val voteAverage: Double = 0.0,
    @SerialName(value = "vote_count")
    val voteCount: Int = 0,
    @SerialName(value = "width")
    val width: Int = 0
)