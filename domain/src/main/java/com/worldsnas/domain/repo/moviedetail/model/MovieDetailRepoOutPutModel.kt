package com.worldsnas.domain.repo.moviedetail.model

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.MovieRepoModel

sealed class MovieDetailRepoOutPutModel {
    class Success(val movie : MovieRepoModel) : MovieDetailRepoOutPutModel()
    class Error(val err : ErrorHolder) : MovieDetailRepoOutPutModel()
}