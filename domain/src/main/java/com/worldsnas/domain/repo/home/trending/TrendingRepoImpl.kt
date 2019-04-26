package com.worldsnas.domain.repo.home.trending

import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.entity.TrendingEntity
import com.worldsnas.domain.getErrorRepoModel
import com.worldsnas.domain.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoOutputModel
import com.worldsnas.domain.repo.home.trending.model.TrendingRepoParamModel
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TrendingRepoImpl @Inject constructor(
        private val fetcher: RFetcher<Int, ResultsServerModel<MovieServerModel>>,
        private val persister: Persister<TrendingPersisterKey, TrendingEntity>,
        private val serverMapper: Mapper<MovieServerModel, MovieEntity>,
        private val serverRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
        private val entityMapper: Mapper<MovieEntity, MovieRepoModel>
) : TrendingRepo {

    private var list = mutableListOf<MovieRepoModel>()

    override fun fetch(param: TrendingRepoParamModel): Single<TrendingRepoOutputModel> =
            fetcher.fetch(param.page)
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

    override fun observeLatest(): Observable<TrendingRepoOutputModel> =
            persister.observe(TrendingPersisterKey())
                    .map {
                        it.movies.map { movie -> entityMapper.map(movie) }
                    }
                    .map {
                        TrendingRepoOutputModel.Success(it, list)
                    }

    override fun observerAndUpdate(): Observable<TrendingRepoOutputModel> {
        val updateObs = fetcher.fetch(1)
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
                                                    serverMapper
                                                            .map(movie)
                                                }
                                    }
                                    .flatMapCompletable {
                                        persister
                                                .write(
                                                        TrendingEntity()
                                                                .apply { movies.addAll(it) })
                                    }
                                    .toObservable()
                    )
                }
        return persister.observe(TrendingPersisterKey())
                .map {
                    it.movies.map { movie -> entityMapper.map(movie) }
                }
                .map {
                    TrendingRepoOutputModel.Success(it, list) as TrendingRepoOutputModel
                }
                .mergeWith(updateObs)
    }

    override fun update(param: TrendingRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error> =
            fetcher.fetch(param.page)
                    .toObservable()
                    .publish { publish ->
                        Observable.merge(
                                publish
                                        .filter { it.isNotSuccessful || it.body() == null }
                                        .map { LatestMovieRepoOutputModel.Error(it.getErrorRepoModel()) },
                                publish
                                        .filter { it.isSuccessful && it.body() != null }
                                        .map {
                                            it
                                                    .body()!!
                                                    .list
                                                    .map { movie ->
                                                        serverMapper
                                                                .map(movie)
                                                    }
                                        }
                                        .flatMapCompletable {
                                            persister
                                                    .write(
                                                            TrendingEntity()
                                                                    .apply { movies.addAll(it) })
                                        }
                                        .toObservable()
                        )
                    }
                    .firstElement()
}