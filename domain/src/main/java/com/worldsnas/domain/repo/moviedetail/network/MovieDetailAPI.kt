package com.worldsnas.domain.repo.moviedetail.network

import com.worldsnas.domain.model.servermodels.MovieServerModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailAPI {

    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieID: Long,
        @Query("append_to_response") appendToResponse: String
    ): Response<MovieServerModel>
}