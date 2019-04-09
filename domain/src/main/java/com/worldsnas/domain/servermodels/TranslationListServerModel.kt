package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class TranslationListServerModel (
    @Json(name= "translations")
    val translations : List<TranslationServerModel> = emptyList()
)