package com.worldsnas.domain.repo.featured

import com.worldsnas.panther.RFetcher
import retrofit2.Response
import javax.inject.Inject

class FeaturedFetcher @Inject constructor(
) : RFetcher<String, Int>{
    override fun fetch(param: String): Response<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}