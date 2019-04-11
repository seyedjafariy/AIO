package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.getErrorRepoModel
import com.worldsnas.domain.isNotSuccessful
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import com.worldsnas.panther.RFetcher
import javax.inject.Inject

class TrendingRepoImpl @Inject constructor(
    private val fetcher : RFetcher<Unit, ResultsServerModel<MovieServerModel>>
): TrendingRepo{

    fun fetchTrends() =
        fetcher.fetch(Unit)
            .map {
                if (it.isNotSuccessful) {
                    it.getErrorRepoModel()
                }
                if (it.isSuccessful){

                }
            }

}