package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationServerModel(
    @SerialName(value = "iso_3166_1")
    val iso_3166_1: String? = "",
    @SerialName(value = "iso_639_1")
    val iso_639_1: String? = "",
    @SerialName(value = "name")
    val name: String? = "",
    @SerialName(value = "english_name")
    val englishName: String? = "",
    @SerialName(value = "data")
    val data: TranslationDataServerModel? = null
)