package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsServerModel(
    @SerialName(value = "cast")
    val casts: List<CastServerModel> = emptyList(),
    @SerialName(value = "crew")
    val crews: List<CrewServerModel> = emptyList()
)