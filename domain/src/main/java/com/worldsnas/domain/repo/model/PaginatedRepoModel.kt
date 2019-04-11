package com.worldsnas.domain.repo.model

data class PaginatedRepoModel<T>(
    val list: List<T>,
    val page: Int = 0,
    val totalPages : Int = 0,
    val totalResults : Int = 0
)