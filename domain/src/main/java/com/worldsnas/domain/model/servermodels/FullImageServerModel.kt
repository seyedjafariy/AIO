package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullImageServerModel(
    @SerialName(value = "backdrops")
    val backdrops: List<ImageServerModel> = emptyList(),
    @SerialName(value = "posters")
    val posters: List<ImageServerModel> = emptyList()
)