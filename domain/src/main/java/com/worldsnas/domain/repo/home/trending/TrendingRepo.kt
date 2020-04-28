package com.worldsnas.domain.repo.home.trending

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import kotlinx.coroutines.flow.Flow

interface TrendingRepo {

    fun fetch(param: TrendingRepoParamModel) : Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun getCache() : Flow<List<MovieRepoModel>>
}