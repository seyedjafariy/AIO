package com.worldsnas.domain.repo.search.movie

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.SearchAPI
import com.worldsnas.domain.repo.search.movie.model.SearchRequestParam
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MovieSearchFetcher @Inject constructor(
    val api: SearchAPI
) : RFetcher<SearchRequestParam, ResultsServerModel<MovieServerModel>> {

    override fun fetch(param: SearchRequestParam): Single<Response<ResultsServerModel<MovieServerModel>>> =
        api.searchMovie(
            param.query,
            param.page,
            param.includeAdult
        ).errorHandler()

}