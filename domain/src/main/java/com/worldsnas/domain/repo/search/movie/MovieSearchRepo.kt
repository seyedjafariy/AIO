package com.worldsnas.domain.repo.search.movie

import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoOutputModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import io.reactivex.Single

interface MovieSearchRepo {

    fun search(param : MovieSearchRepoParamModel) : Single<MovieSearchRepoOutputModel>

    fun getCache() : Single<MovieSearchRepoOutputModel>
}