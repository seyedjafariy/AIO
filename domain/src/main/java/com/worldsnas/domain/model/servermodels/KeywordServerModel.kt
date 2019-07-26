package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable


@JsonSerializable
data class KeywordServerModel(
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "name")
    val name: String = ""
)