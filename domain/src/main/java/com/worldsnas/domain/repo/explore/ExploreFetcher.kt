package com.worldsnas.domain.repo.explore

import com.worldsnas.panther.RFetcher
import retrofit2.Response
import javax.inject.Inject

class ExploreFetcher @Inject constructor(
): RFetcher<String, String>  {
    override fun fetch(param: String): Response<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}