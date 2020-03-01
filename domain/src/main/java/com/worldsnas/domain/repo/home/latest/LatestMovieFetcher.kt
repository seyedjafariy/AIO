package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.helpers.toStringDate
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.panther.Fetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LatestMovieFetcher @Inject constructor(
    private val api: HomeAPI
) : Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>> {
    override fun fetch(param: LatestMovieRequestParam): Flow<Response<ResultsServerModel<MovieServerModel>>> =
        flow {
            emit(
                api.getLatestMovie(
                    param.date.toStringDate(),
                    param.page
                )
            )
        }.errorHandler()
            .flowOn(Dispatchers.IO)
}