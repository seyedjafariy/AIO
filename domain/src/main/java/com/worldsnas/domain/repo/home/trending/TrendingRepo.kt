package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.repo.home.latest.LatestMovieRepoOutputModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoParamModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import io.reactivex.Maybe
import io.reactivex.Observable

interface TrendingRepo {

    fun observeLatest(): Observable<TrendingRepoOutputModel>
    fun observerAndUpdate(): Observable<TrendingRepoOutputModel>
    fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error>
}