package com.worldsnas.domain.repo.search.keywords

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import io.reactivex.Single

interface SearchKeywordsRepo {

    fun keywordsFor(
        param: SearchKeywordRepoParamModel
    ): Single<Either<ErrorHolder, List<KeywordRepoModel>>>

    fun nextPage(): Single<Either<ErrorHolder, List<KeywordRepoModel>>>
    fun cachedKeywords(): Single<List<KeywordRepoModel>>
}