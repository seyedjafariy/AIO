package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LanguageServerModel(
    @SerialName(value = "iso_639_1")
    val iso: String = "",
    @SerialName(value = "name")
    val name: String
)