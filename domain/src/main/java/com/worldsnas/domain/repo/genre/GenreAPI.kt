package com.worldsnas.domain.repo.genre

import com.worldsnas.domain.model.servermodels.GenreListServerModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface GenreAPI {

    @GET("/genre/movie/list")
    fun allMovieGenre(
    ): Single<Response<GenreListServerModel>>
}