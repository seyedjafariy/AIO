package com.worldsnas.domain.repo.home.latest

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface LatestMovieRepo {
    fun observeLatest(): Observable<LatestMovieRepoOutputModel>
    fun observerAndUpdate(): Observable<LatestMovieRepoOutputModel>
    fun fetch(param: LatestMovieRepoParamModel) : Single<LatestMovieRepoOutputModel>
    fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error>
}