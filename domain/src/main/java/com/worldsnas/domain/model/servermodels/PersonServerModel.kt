package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonServerModel(
    @SerialName(value = "id")
    val id: Long = 0,
    @SerialName(value = "popularity")
    val popularity: Double = 0.0,
    @SerialName(value = "profile_path")
    val profilePath: String = "",
    @SerialName(value = "name")
    val name: String = "",
    @SerialName(value = "adult")
    val adult: Boolean = false
)