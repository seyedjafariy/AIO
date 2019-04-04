package com.worldsnas.domain.repo.explore

import com.worldsnas.panther.RFetcher
import javax.inject.Inject

class ExploreRepoImpl @Inject constructor(
    private val fetcher: RFetcher<String, String>
): ExploreRepo {

    fun loadExplore(){

    }
}