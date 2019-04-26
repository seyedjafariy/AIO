package com.worldsnas.domain.repo.home.latest

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.MovieRepoModel

sealed class LatestMovieRepoOutputModel {

    class Success(
            val thisPage: List<MovieRepoModel>,
            val all : List<MovieRepoModel>) : LatestMovieRepoOutputModel()
    class Error(val err: ErrorHolder) : LatestMovieRepoOutputModel()
}