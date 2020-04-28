package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalIdsSeverModel(
    @SerialName(value = "imdb_id")
    val imdbId: String = "",
    @SerialName(value = "facebook_id")
    val facebookId: String = "",
    @SerialName(value = "instagram_id")
    val instagramId: String = "",
    @SerialName(value = "twitter_id")
    val twitterId: String = ""
)