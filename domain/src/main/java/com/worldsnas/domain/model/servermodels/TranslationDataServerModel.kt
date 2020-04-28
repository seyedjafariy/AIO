package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationDataServerModel (
    @SerialName(value = "title")
    val title: String = "",
    @SerialName(value = "overview")
    val overview: String = "",
    @SerialName(value = "homepage")
    val homePage: String = ""
)