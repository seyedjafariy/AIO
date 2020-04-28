package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountryServerModel(
    @SerialName(value = "iso_3166_1")
    val iso: String = "",
    @SerialName(value = "name")
    val name: String = ""
)