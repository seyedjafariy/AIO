package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.helpers.toStringDate
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.panther.Fetcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LatestMovieFetcher @Inject constructor(
    private val api: HomeAPI
) : Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>> {
    override suspend fun fetch(param: LatestMovieRequestParam): Response<ResultsServerModel<MovieServerModel>> =
        withContext(Dispatchers.IO) {
            api.getLatestMovie(
                param.date.toStringDate(),
                param.page
            )
        }
}