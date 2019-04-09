package com.worldsnas.domain.repo.home

import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeAPI {

    @GET("/3/discover/movie")
    fun getLatestMovie(@Query("page") page: Int):
        Single<Response<ResultsServerModel<MovieServerModel>>>
}