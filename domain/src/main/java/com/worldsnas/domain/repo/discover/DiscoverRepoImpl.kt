package com.worldsnas.domain.repo.discover

import com.worldsnas.panther.RFetcher
import javax.inject.Inject

class DiscoverRepoImpl @Inject constructor(
    private val fetcher: RFetcher<String, String>
): DiscoverRepo {

    fun loadExplore(){

    }
}