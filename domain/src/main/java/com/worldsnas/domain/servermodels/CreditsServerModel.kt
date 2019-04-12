package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CreditsServerModel(
    @Json(name= "cast")
    val casts : List<CastServerModel> = emptyList(),
    @Json(name= "crew")
    val crews : List<CrewServerModel> = emptyList()
)