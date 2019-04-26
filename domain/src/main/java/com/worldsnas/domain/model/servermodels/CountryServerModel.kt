package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CountryServerModel(
    @Json(name = "iso_3166_1")
    val iso: String = "",
    @Json(name = "name")
    val name: String = ""
)