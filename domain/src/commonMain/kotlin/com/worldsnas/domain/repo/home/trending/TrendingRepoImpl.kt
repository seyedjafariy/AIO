package com.worldsnas.domain.repo.home.trending

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.Mapper
import com.worldsnas.core.suspendToFlow
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TrendingRepoImpl(
    private val api: HomeAPI,
    private val serverRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
) : TrendingRepo {

    private var list = mutableListOf<MovieRepoModel>()

    override fun fetch(param: TrendingRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        suspendToFlow {
            api.getTrendingMovie()
        }
            .errorHandler()
            .eitherError {
                val updated = it.list
                    .map { movie ->
                        serverRepoMapper
                            .map(movie)
                    }
                    .toMutableList()

                list = updated
                updated
            }

    override fun getCache(): Flow<List<MovieRepoModel>> =
        flowOf(list)

}