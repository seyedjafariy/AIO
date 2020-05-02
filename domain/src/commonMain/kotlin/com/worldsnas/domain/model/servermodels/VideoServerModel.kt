package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoServerModel(
    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "iso_639_1")
    val iso_639_1: String? = "",
    @SerialName(value = "iso_3166_1")
    val iso_3166_1: String? = "",
    @SerialName(value = "key")
    val key: String = "",
    @SerialName(value = "name")
    val name: String = "",
    @SerialName(value = "site")
    val site: String = "",
    @SerialName(value = "size")
    val size: Int = 0,
    @SerialName(value = "type")
    val type: String = ""
)