package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class TranslationDataServerModel (
    @Json(name = "title")
    val title: String = "",
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "homepage")
    val homePage: String = ""
)