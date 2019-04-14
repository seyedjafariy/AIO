package com.worldsnas.domain.repo.home.latest

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.repomodel.MovieRepoModel

sealed class LatestMovieRepoOutputModel {

    class Success(val movies: List<MovieRepoModel>) : LatestMovieRepoOutputModel()
    class Error(val err: ErrorHolder) : LatestMovieRepoOutputModel()
}