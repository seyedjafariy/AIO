package com.worldsnas.domain.repo.search.keywords

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.movie.model.SearchRequestParam
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import javax.inject.Inject

class SearchKeywordsRepoImpl @Inject constructor(
    private val fetcher: RFetcher<SearchRequestParam, ResultsServerModel<KeywordServerModel>>,
    private val mapper: Mapper<KeywordServerModel, KeywordRepoModel>
) : SearchKeywordsRepo {

    private var query = ""
    private var nextPage: Int = 1
    private var keywords: MutableList<KeywordRepoModel> = mutableListOf()

    override fun keywordsFor(param: SearchKeywordRepoParamModel): Single<Either<ErrorHolder, List<KeywordRepoModel>>> =
        fetcher.fetch(
            SearchRequestParam(
                param.query,
                param.page
            )
        ).eitherError { result ->
            result.list.map { keyword ->
                mapper.map(keyword)
            }.also {
                if (query != param.query || (query == param.query && param.page == 1))
                    keywords = it.toMutableList()
                else if (query == param.query && param.page != 1)
                    keywords.addAll(it)

                nextPage = param.page + 1
                query = param.query

            }
        }

    override fun nextPage(): Single<Either<ErrorHolder, List<KeywordRepoModel>>> =
        keywordsFor(
            SearchKeywordRepoParamModel(
                query,
                nextPage
            )
        )

    override fun cachedKeywords(): Single<List<KeywordRepoModel>> =
        Single.just(keywords)
}