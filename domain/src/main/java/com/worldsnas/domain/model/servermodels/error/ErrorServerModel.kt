package com.worldsnas.domain.model.servermodels.error

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ErrorServerModel(
    @Json(name= "status_message")
    val message : String,
    @Json(name="status_code")
    val code : Int
)