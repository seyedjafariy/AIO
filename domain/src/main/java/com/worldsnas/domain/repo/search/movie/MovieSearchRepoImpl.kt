package com.worldsnas.domain.repo.search.movie

import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoOutputModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRequestParam
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MovieSearchRepoImpl @Inject constructor(
    private val fetcher: Fetcher<MovieSearchRequestParam, Response<ResultsServerModel<MovieServerModel>>>,
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
) : MovieSearchRepo {

    private var searchCache = mutableListOf<MovieRepoModel>()

    override fun search(param: MovieSearchRepoParamModel): Single<MovieSearchRepoOutputModel> =
        fetcher.fetch(
            MovieSearchRequestParam(
                param.query,
                param.page
            )
        )
            .toObservable()
            .publish { publish ->
                Observable.merge(
                    publish
                        .filter { it.isNotSuccessful || it.body() == null }
                        .map { MovieSearchRepoOutputModel.Error(it.getErrorRepoModel()) },
                    publish
                        .filter { it.isSuccessful && it.body() != null }
                        .map {
                            it
                                .body()!!
                                .list
                                .map { movie ->
                                    movieServerRepoMapper
                                        .map(movie)
                                }
                        }
                        .doOnNext{
                            if (param.page == 1) {
                                searchCache = it.toMutableList()
                            }else{
                                searchCache.addAll(it)
                            }
                        }
                        .map {
                            MovieSearchRepoOutputModel.Success(
                                it,
                                searchCache
                            )
                        }
                )
            }.firstOrError()

    override fun getCache(): Single<MovieSearchRepoOutputModel> =
            Single.just(
                MovieSearchRepoOutputModel.Success(
                    emptyList(),
                    searchCache
                )
            )
}