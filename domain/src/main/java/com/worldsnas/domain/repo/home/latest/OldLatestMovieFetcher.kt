package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class OldLatestMovieFetcher @Inject constructor(
    private val api : HomeAPI
): RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>  {
    override fun fetch(param: LatestMovieRequestParam): Single<Response<ResultsServerModel<MovieServerModel>>> =
        api.getLatestMovieOld(param.page)
            .errorHandler()
}