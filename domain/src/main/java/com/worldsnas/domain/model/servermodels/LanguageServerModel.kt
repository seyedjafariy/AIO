package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class LanguageServerModel(
    @Json(name = "iso_639_1")
    val iso: String = "",
    @Json(name = "name")
    val name: String
)