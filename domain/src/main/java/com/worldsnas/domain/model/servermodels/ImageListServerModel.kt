package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageListServerModel (
    @SerialName(value= "profile")
    val profile : List<ImageServerModel> = emptyList()
)