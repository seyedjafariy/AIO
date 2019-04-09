package com.worldsnas.domain.repo.home.discover

import com.worldsnas.panther.RFetcher
import javax.inject.Inject

class LatestMovieRepoImpl @Inject constructor(
    private val fetcher: RFetcher<String, String>
): LatestMovieRepo {

    fun loadExplore(){

    }
}