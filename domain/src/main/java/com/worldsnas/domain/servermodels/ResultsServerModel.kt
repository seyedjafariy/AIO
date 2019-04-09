package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ResultsServerModel<T>(
    @Json(name = "results")
    val list: List<T>,
    @Json(name = "page")
    val page: Int = 0,
    @Json(name= "total_pages")
    val totalPages : Int = 0,
    @Json(name= "total_results")
    val totalResults : Int = 0
)