package com.worldsnas.domain.repomodel

data class PaginatedRepoModel<T>(
    val list: List<T>,
    val page: Int = 0,
    val totalPages : Int = 0,
    val totalResults : Int = 0
)