package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsServerModel<T>(
    @SerialName(value = "results")
    val list: List<T> = emptyList(),
    @SerialName(value = "page")
    val page: Long = 0,
    @SerialName(value = "total_pages")
    val totalPages: Int = 0,
    @SerialName(value = "total_results")
    val totalResults: Int = 0
)