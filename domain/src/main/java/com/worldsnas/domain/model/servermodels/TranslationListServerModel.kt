package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationListServerModel(
    @SerialName(value = "translations")
    val translations: List<TranslationServerModel> = emptyList()
)