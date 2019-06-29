package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class TrendingFetcher @Inject constructor(
    private val api: HomeAPI
) : RFetcher<Int, ResultsServerModel<MovieServerModel>> {

    override fun fetch(param: Int): Single<Response<ResultsServerModel<MovieServerModel>>> =
        api.getTerndingMovie()
            .errorHandler()
}