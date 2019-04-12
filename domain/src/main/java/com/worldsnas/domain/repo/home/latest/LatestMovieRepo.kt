package com.worldsnas.domain.repo.home.latest

import io.reactivex.Maybe
import io.reactivex.Observable

interface LatestMovieRepo {
    fun observeLatest(): Observable<LatestMovieRepoOutputModel>
    fun observerAndUpdate(): Observable<LatestMovieRepoOutputModel>
    fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error>
}