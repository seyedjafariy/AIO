package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ExternalIdsSeverModel (
    @Json(name = "imdb_id")
    val imdbId: String = "",
    @Json(name = "facebook_id")
    val facebookId: String = "",
    @Json(name = "instagram_id")
    val instagramId: String = "",
    @Json(name = "twitter_id")
    val twitterId: String = ""
)