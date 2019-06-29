package com.worldsnas.domain.repo.search

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("/3/search/movie")
    fun searchMovie(
        @Query("query") query : String,
        @Query("page") page : Int,
        @Query("include_adult") includeAdult : Boolean
    ): Single<Response<ResultsServerModel<MovieServerModel>>>
}