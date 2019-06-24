package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class LatestMovieRepoImpl @Inject constructor(
        private val fetcher: RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
        private val latestMoviePersister: Persister<LatestMoviePersisterKey, List<@JvmSuppressWildcards MovieEntity>>,
        private val movieServerMapper: Mapper<MovieServerModel, MovieEntity>,
        private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
        private val movieEntityMapper: Mapper<MovieEntity, MovieRepoModel>
) : LatestMovieRepo {

    var list: MutableList<MovieRepoModel> = mutableListOf()

    override fun memCache(): Single<LatestMovieRepoOutputModel.Success> =
        Single.just(
            LatestMovieRepoOutputModel.Success(
                emptyList(),
                list
            )
        )

    override fun observeLatest(): Observable<LatestMovieRepoOutputModel> =
            latestMoviePersister.observe(LatestMoviePersisterKey())
                    .map {
                        it.map { movie -> movieEntityMapper.map(movie) }
                    }
                    .map {
                        LatestMovieRepoOutputModel.Success(it, list)
                    }

    override fun observerAndUpdate(): Observable<LatestMovieRepoOutputModel> {
        val updateObs = fetcher.fetch(LatestMovieRequestParam(1))
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
                                                    movieServerMapper
                                                            .map(movie)
                                                }
                                    }
                                    .flatMapCompletable {
                                        latestMoviePersister
                                                .write(it)
                                    }
                                    .toObservable()
                    )
                }
        return latestMoviePersister.observe(LatestMoviePersisterKey())
                .map {
                    it.map { movie -> movieEntityMapper.map(movie) }
                }
                .map {
                    LatestMovieRepoOutputModel.Success(it, list) as LatestMovieRepoOutputModel
                }
                .mergeWith(updateObs)
    }

    override fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error> =
            fetcher.fetch(LatestMovieRequestParam(param.page))
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
                                                        movieServerMapper
                                                                .map(movie)
                                                    }
                                        }
                                        .flatMapCompletable {
                                            latestMoviePersister
                                                    .write(it)
                                        }
                                        .toObservable()
                        )
                    }
                    .firstElement()

    override fun fetch(param: LatestMovieRepoParamModel): Single<LatestMovieRepoOutputModel> =
            fetcher.fetch(LatestMovieRequestParam(param.page))
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
                                                        movieServerRepoMapper
                                                                .map(movie)
                                                    }
                                        }
                                        .doOnNext {
                                            if (param.page == 1) {
                                                list = it.toMutableList()
                                            }else{
                                                list.addAll(it)
                                            }
                                        }
                                        .map {
                                            LatestMovieRepoOutputModel.Success(it, list)
                                        }
                        )
                    }
                    .firstOrError()
}