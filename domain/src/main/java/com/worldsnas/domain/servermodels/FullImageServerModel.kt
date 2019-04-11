package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class FullImageServerModel(
    @Json(name = "backdrops")
    val backdrops: List<ImageServerModel> = emptyList(),
    @Json(name = "posters")
    val posters: List<ImageServerModel> = emptyList()
)