package com.worldsnas.domain.repo.moviedetail

import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import io.reactivex.Single

interface MovieDetailRepo {

    fun getMovieDetail(param: MovieDetailRepoParamModel): Single<MovieDetailRepoOutPutModel>

    fun getCached() : Single<MovieDetailRepoOutPutModel>
}