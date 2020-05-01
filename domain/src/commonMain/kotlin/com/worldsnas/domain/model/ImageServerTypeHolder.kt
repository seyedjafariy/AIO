package com.worldsnas.domain.model

import com.worldsnas.domain.helpers.ImageType
import com.worldsnas.domain.model.servermodels.ImageServerModel

data class ImageServerTypeHolder(
    val image: ImageServerModel,
    val type: ImageType
)