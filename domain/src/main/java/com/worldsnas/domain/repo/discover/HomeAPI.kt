package com.worldsnas.domain.repo.discover

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeAPI {

    @GET("/3/discover/movie")
    fun getDiscover(@Query("page") page: Int) : Single<String>


}