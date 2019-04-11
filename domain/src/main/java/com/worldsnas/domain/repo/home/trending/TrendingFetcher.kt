package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class TrendingFetcher @Inject constructor(
    private val api: HomeAPI
) : RFetcher<Unit, ResultsServerModel<MovieServerModel>> {

    override fun fetch(param: Unit): Single<Response<ResultsServerModel<MovieServerModel>>> =
        api.getTerndingMovie()
}