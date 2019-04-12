package com.worldsnas.domain.repo.home.trending.model

import com.worldsnas.domain.repomodel.ErrorRepoModel
import com.worldsnas.domain.repomodel.MovieRepoModel

sealed class TrendingRepoOutputModel {

    data class Success(val movies: List<MovieRepoModel>) : TrendingRepoOutputModel()
    data class Error(val err: ErrorRepoModel) : TrendingRepoOutputModel()
}