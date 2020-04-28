package com.worldsnas.domain.repo.search

import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("/3/search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("include_adult")
        includeAdult: Boolean
    ): Response<ResultsServerModel<MovieServerModel>>

    @GET("/3/search/keyword")
    suspend fun searchKeywords(
        @Query("query")
        query: String,
        @Query("page")
        page: Int,
        @Query("include_adult")
        includeAdult: Boolean
    ) : Response<ResultsServerModel<KeywordServerModel>>
}