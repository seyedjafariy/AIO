package com.worldsnas.domain.repo.home.trending.model

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.MovieRepoModel

sealed class TrendingRepoOutputModel {

    data class Success(
            val thisPage: List<MovieRepoModel>,
            val allPages: List<MovieRepoModel>) : TrendingRepoOutputModel()

    data class Error(val err: ErrorHolder) : TrendingRepoOutputModel()
}