package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KeywordServerModel(
    @SerialName(value = "id")
    val id: Long = 0,
    @SerialName(value = "name")
    val name: String = ""
)