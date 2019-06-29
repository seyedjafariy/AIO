package com.worldsnas.domain.repo.search.movie.model

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.MovieRepoModel

sealed class MovieSearchRepoOutputModel {
    class Success(
        val thisPage: List<MovieRepoModel>,
        val all: List<MovieRepoModel>
    ) : MovieSearchRepoOutputModel()

    class Error(
        val err : ErrorHolder
    ) : MovieSearchRepoOutputModel()
}