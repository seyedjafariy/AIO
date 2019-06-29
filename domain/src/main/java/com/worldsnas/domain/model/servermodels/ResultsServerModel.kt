package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ResultsServerModel<T>(
    @Json(name = "results")
    val list: List<T> = emptyList(),
    @Json(name = "page")
    val page: Long = 0,
    @Json(name= "total_pages")
    val totalPages : Int = 0,
    @Json(name= "total_results")
    val totalResults : Int = 0
)