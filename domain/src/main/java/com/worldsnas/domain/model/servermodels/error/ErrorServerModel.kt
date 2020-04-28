package com.worldsnas.domain.model.servermodels.error

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorServerModel(
    @SerialName(value = "status_message")
    val message: String,
    @SerialName(value = "status_code")
    val code: Int
)