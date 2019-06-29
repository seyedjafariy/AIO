package com.worldsnas.domain.repo.search.movie

import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoOutputModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import io.reactivex.Single
import javax.inject.Inject

class MovieSearchRepoImpl @Inject constructor(

): MovieSearchRepo {
    override fun search(param: MovieSearchRepoParamModel): Single<MovieSearchRepoOutputModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCache(): Single<MovieSearchRepoOutputModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}