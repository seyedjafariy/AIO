package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenreListServerModel(
    @SerialName(value = "genres")
    val genres: List<GenreServerModel> = emptyList()
)