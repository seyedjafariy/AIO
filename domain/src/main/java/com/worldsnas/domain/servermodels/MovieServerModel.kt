package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class MovieServerModel(
    @Json(name= "id")
    val id : Long = 0
)