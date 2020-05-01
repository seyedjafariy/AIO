package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyServerModel(
    @SerialName(value = "id")
    val id: Long = 0,
    @SerialName(value = "logo_path")
    val logoPath: String = "",
    @SerialName(value = "name")
    val name: String = "",
    @SerialName(value = "origin_country")
    val originCountry: String = ""
)