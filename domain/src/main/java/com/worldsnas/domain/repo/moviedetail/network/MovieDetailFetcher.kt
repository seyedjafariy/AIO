package com.worldsnas.domain.repo.moviedetail.network

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRequestModel
import com.worldsnas.panther.Fetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MovieDetailFetcher @Inject constructor(
    private val api: MovieDetailAPI
) : Fetcher<MovieDetailRequestModel, Response<MovieServerModel>> {
    override fun fetch(param: MovieDetailRequestModel): Single<Response<MovieServerModel>> =
        api
            .getMovie(
                param.movieId,
                param.appendToResponse
            )
            .errorHandler()
}