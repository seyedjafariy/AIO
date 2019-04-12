package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ImageListServerModel (
    @Json(name= "profile")
    val profile : List<ImageServerModel> = emptyList()
)