package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class GenreListServerModel(
    @Json(name= "genres")
    val genres : List<GenreServerModel> = emptyList()
)