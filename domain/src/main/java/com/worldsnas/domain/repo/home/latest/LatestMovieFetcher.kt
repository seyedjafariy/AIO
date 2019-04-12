package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class LatestMovieFetcher @Inject constructor(
    private val api : HomeAPI
): RFetcher<Int, ResultsServerModel<MovieServerModel>>  {
    override fun fetch(param: Int): Single<Response<ResultsServerModel<MovieServerModel>>> =
        api.getLatestMovie(param)
}