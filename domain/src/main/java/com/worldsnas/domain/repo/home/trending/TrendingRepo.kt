package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.repo.home.latest.LatestMovieRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface TrendingRepo {

    fun fetch(param: TrendingRepoParamModel) : Single<TrendingRepoOutputModel>
    fun observeLatest(): Observable<TrendingRepoOutputModel>
    fun observerAndUpdate(): Observable<TrendingRepoOutputModel>
    fun update(param: TrendingRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error>
}