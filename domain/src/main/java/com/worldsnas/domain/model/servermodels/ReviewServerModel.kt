package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ReviewServerModel(
    @Json(name = "author")
    val author: String = "",
    @Json(name = "content")
    val content: String = "",
    @Json(name = "id")
    val id: String = "",
    @Json(name = "url")
    val url: String = ""
)