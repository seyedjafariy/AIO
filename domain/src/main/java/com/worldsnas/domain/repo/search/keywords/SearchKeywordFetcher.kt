package com.worldsnas.domain.repo.search.keywords

import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.SearchAPI
import com.worldsnas.domain.repo.search.movie.model.SearchRequestParam
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class SearchKeywordFetcher @Inject constructor(
    private val api: SearchAPI
) : RFetcher<SearchRequestParam, ResultsServerModel<KeywordServerModel>> {
    override fun fetch(param: SearchRequestParam): Single<Response<ResultsServerModel<KeywordServerModel>>> =
        api.searchKeywords(
            param.query,
            param.page,
            param.includeAdult
        ).errorHandler()
}