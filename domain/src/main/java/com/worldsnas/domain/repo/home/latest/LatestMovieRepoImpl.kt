package com.worldsnas.domain.repo.home.latest

import com.worldsnas.domain.entity.LatestMovieEntity
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.getErrorRepoModel
import com.worldsnas.domain.isNotSuccessful
import com.worldsnas.domain.repomodel.MovieRepoModel
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import io.reactivex.Maybe
import io.reactivex.Observable
import javax.inject.Inject

class LatestMovieRepoImpl @Inject constructor(
    private val fetcher: RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val latestMoviePersister: Persister<LatestMoviePersisterKey, LatestMovieEntity>,
    private val movieServerMapper: Mapper<MovieServerModel, MovieEntity>,
    private val movieEntityMapper: Mapper<MovieEntity, MovieRepoModel>
) : LatestMovieRepo {

    override fun observeLatest(): Observable<LatestMovieRepoOutputModel> =
        latestMoviePersister.observe(LatestMoviePersisterKey())
            .map {
                it.movies.map { movie -> movieEntityMapper.map(movie) }
            }
            .map {
                LatestMovieRepoOutputModel.Success(it)
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
                                .write(
                                    LatestMovieEntity()
                                        .apply { movies.addAll(it) })
                        }
                        .toObservable()
                )
            }
        return latestMoviePersister.observe(LatestMoviePersisterKey())
            .map {
                it.movies.map { movie -> movieEntityMapper.map(movie) }
            }
            .map {
                LatestMovieRepoOutputModel.Success(it) as LatestMovieRepoOutputModel
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
                                .write(
                                    LatestMovieEntity()
                                        .apply { movies.addAll(it) })
                        }
                        .toObservable()
                )
            }
            .firstElement()
}