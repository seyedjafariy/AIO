package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.worldsnas.core.ErrorHolder
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class LatestMovieRepoImpl @Inject constructor(
    private val oldFetcher: RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val fetcher: Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val latestMoviePersister: Persister<LatestMoviePersisterKey, List<@JvmSuppressWildcards MovieEntity>>,
    private val movieServerMapper: Mapper<MovieServerModel, MovieEntity>,
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
    private val movieEntityMapper: Mapper<MovieEntity, MovieRepoModel>,
    private val moviePersister: MoviePersister
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

    override fun receiveAndUpdate(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> = flow {
        val entireDb: List<MovieRepoModel> =
            moviePersister.observeMovies()
                .take(1)
                .toList()
                .flatten()
                .map {
                    MovieRepoModel(
                        it.id,
                        backdropPath = it.backdropImage ?: "",
                        posterPath = it.posterImage ?: "",
                        title = it.title ?: ""
                    )
                }

        val serverFirstPageResponse = fetcher.fetch(LatestMovieRequestParam(0))

        if (serverFirstPageResponse.isNotSuccessful || serverFirstPageResponse.body() == null) {
            list = entireDb.toMutableList()
            emit(entireDb.right())
            emit(serverFirstPageResponse.getErrorRepoModel().left())
            return@flow
        }

        val serverFirstPage: List<MovieRepoModel> = serverFirstPageResponse.body()!!.list.map {
            movieServerRepoMapper.map(it)
        }

        val dbValid = entireDb.any { dbItem ->
            serverFirstPage.find { serverItem ->
                dbItem.id == serverItem.id
            }?.let { true } ?: false
        }

        if (dbValid) {
            val updatedDB = entireDb.toMutableList().apply {
                removeAll { entity ->
                    serverFirstPage.find { dto -> entity.id == dto.id } != null
                }
            }

            list.addAll(serverFirstPage)
            list.addAll(updatedDB)
        } else {
            moviePersister.clearMovies()
            list.clear()
            list.addAll(serverFirstPage)
        }

        emit(list.right())

        moviePersister.insertMovies(serverFirstPage.map {
            Movie.Impl(it.id, it.originalTitle, it.backdropPath, it.posterPath)
        })
    }

    override fun update(param: LatestMovieRepoParamModel): Maybe<LatestMovieRepoOutputModel.Error> =
        oldFetcher.fetch(LatestMovieRequestParam(param.page))
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
        oldFetcher.fetch(LatestMovieRequestParam(param.page))
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
                            } else {
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
