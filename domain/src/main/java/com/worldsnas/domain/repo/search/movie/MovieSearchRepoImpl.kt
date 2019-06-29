package com.worldsnas.domain.repo.search.movie

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoOutputModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRequestParam
import com.worldsnas.panther.Fetcher
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MovieSearchRepoImpl @Inject constructor(
    private val fetcher: Fetcher<MovieSearchRequestParam, Response<ResultsServerModel<MovieServerModel>>>
): MovieSearchRepo {

    override fun search(param: MovieSearchRepoParamModel): Single<MovieSearchRepoOutputModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCache(): Single<MovieSearchRepoOutputModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}