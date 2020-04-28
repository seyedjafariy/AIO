package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewServerModel(
    @SerialName(value = "author")
    val author: String = "",
    @SerialName(value = "content")
    val content: String = "",
    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "url")
    val url: String = ""
)