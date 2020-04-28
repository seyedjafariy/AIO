package com.worldsnas.domain.repo.home

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeAPI {

    @GET("/3/discover/movie?include_video=false&include_adult=false&sort_by=release_date.desc")
    suspend fun getLatestMovie(
        @Query("release_date.lte")
        finalDate: String,
        @Query("page")
        page: Int
    ): Response<ResultsServerModel<MovieServerModel>>

    @GET("/3/trending/movie/day")
    suspend fun getTrendingMovie(): Response<ResultsServerModel<MovieServerModel>>
}