package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class GenreServerModel(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = ""
)