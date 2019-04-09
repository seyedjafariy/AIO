package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class FullImageServerModel(
    @Json(name = "backdrops")
    val backdropsEntity: List<ImageServerModel> = emptyList(),
    @Json(name = "posters")
    val postersEntity: List<ImageServerModel> = emptyList()
)