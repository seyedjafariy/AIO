package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface LatestMovieRepo {
    fun observeLatest(): Observable<LatestMovieRepoOutputModel>
    fun receiveAndUpdate(param : PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun fetch(param: LatestMovieRepoParamModel) : Single<LatestMovieRepoOutputModel>
    fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error>
    fun memCache() : Single<LatestMovieRepoOutputModel.Success>
}