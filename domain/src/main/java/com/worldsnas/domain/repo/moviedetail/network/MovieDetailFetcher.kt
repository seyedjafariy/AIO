package com.worldsnas.domain.repo.moviedetail.network

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRequestModel
import com.worldsnas.panther.Fetcher
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MovieDetailFetcher @Inject constructor(
    private val api: MovieDetailAPI
) : Fetcher<MovieDetailRequestModel, MovieServerModel> {
    override fun fetch(param: MovieDetailRequestModel): Flow<Response<MovieServerModel>> =
        flow {
            emit(
                api
                    .getMovie(
                        param.movieId,
                        param.appendToResponse
                    )
            )
        }.errorHandler()
            .flowOn(Dispatchers.IO)
}