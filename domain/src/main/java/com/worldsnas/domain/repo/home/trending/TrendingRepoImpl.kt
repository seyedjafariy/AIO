package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import com.worldsnas.core.Mapper
import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.repo.home.HomeAPI
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TrendingRepoImpl @Inject constructor(
    private val api: HomeAPI,
    private val serverRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
) : TrendingRepo {

    private var list = mutableListOf<MovieRepoModel>()

    override fun fetch(param: TrendingRepoParamModel): Single<TrendingRepoOutputModel> =
        api.getTerndingMovie()
            .errorHandler()
            .toObservable()
            .publish { publish ->
                Observable.merge(
                    publish
                        .filter { it.isNotSuccessful || it.body() == null }
                        .map { TrendingRepoOutputModel.Error(it.getErrorRepoModel()) },
                    publish
                        .filter { it.isSuccessful && it.body() != null }
                        .map {
                            it
                                .body()!!
                                .list
                                .map { movie ->
                                    serverRepoMapper
                                        .map(movie)
                                }
                                .toMutableList()
                        }
                        .doOnNext {
                            if (param.page == 1)
                                list = it
                            else
                                list.addAll(it)
                        }
                        .map { TrendingRepoOutputModel.Success(it, list) }
                )
            }
            .firstOrError()

    override fun getCache(): Single<TrendingRepoOutputModel.Success> =
        Single.just(list)
            .map {
                TrendingRepoOutputModel.Success(emptyList(), it)
            }

}