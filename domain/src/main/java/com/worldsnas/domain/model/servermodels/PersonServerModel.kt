package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PersonServerModel(
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "profile_path")
    val profilePath: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "adult")
    val adult: Boolean = false
)