package com.worldsnas.domain.repo.search.movie

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.Mapper
import com.worldsnas.core.suspendToFlow
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.search.SearchAPI
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MovieSearchRepo {

    fun search(param: MovieSearchRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>

    fun getCache(): Flow<List<MovieRepoModel>>
}

class MovieSearchRepoImpl(
    private val api: SearchAPI,
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
) : MovieSearchRepo {

    private var searchCache = mutableListOf<MovieRepoModel>()

    override fun search(param: MovieSearchRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        suspendToFlow {
            api.searchMovie(
                param.query,
                param.page,
                false
            )
        }
            .errorHandler()
            .eitherError { results ->
                results.list.map { movie ->
                    movieServerRepoMapper
                        .map(movie)
                }.also {
                    if (param.page == 1) {
                        searchCache = it.toMutableList()
                    } else {
                        searchCache.addAll(it)
                    }
                }
            }

    override fun getCache(): Flow<List<MovieRepoModel>> =
        flowOf(searchCache)
}

