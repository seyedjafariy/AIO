package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class TranslationServerModel (
    @Json(name = "iso_3166_1")
    val iso_3166_1: String = "",
    @Json(name = "iso_639_1")
    val iso_639_1: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "english_name")
    val englishName: String = "",
    @Json(name= "data")
    val data : TranslationDataServerModel? = null
)