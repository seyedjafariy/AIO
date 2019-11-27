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
import kotlinx.coroutines.flow.*
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

    override fun observerAndUpdate(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> = flow {
        val entireDb: List<MovieRepoModel> = moviePersister.observeMovies().first()
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

        if (!dbValid) {
            moviePersister.clearMovies()
            list.clear()
        } else {
            list.addAll(entireDb)
        }

        list.addAll(0, serverFirstPage)
        emit(list.right())

        moviePersister.insertMovies(serverFirstPage.map {
            Movie.Impl(it.id, it.originalTitle, it.backdropPath, it.posterPath)
        })


    }.flatMapConcat {
        moviePersister.observeMovies()
            .map {
                it.map { movie ->
                    MovieRepoModel(
                        movie.id,
                        backdropPath = movie.backdropImage ?: "",
                        posterPath = movie.posterImage ?: "",
                        title = movie.title ?: ""
                    )
                }
            }
            .drop(1)
            .map {
                it.right()
            }
    }

    //        val updateObs = oldFetcher.fetch(LatestMovieRequestParam(1))
//            .toObservable()
//            .publish { publish ->
//                Observable.merge(
//                    publish
//                        .filter { it.isNotSuccessful || it.body() == null }
//                        .map { LatestMovieRepoOutputModel.Error(it.getErrorRepoModel()) },
//                    publish
//                        .filter { it.isSuccessful && it.body() != null }
//                        .map {
//                            it
//                                .body()!!
//                                .list
//                                .map { movie ->
//                                    movieServerMapper
//                                        .map(movie)
//                                }
//                        }
//                        .flatMapCompletable {
//                            latestMoviePersister
//                                .write(it)
//                        }
//                        .toObservable()
//                )
//            }
//        return latestMoviePersister.observe(LatestMoviePersisterKey())
//            .map {
//                it.map { movie -> movieEntityMapper.map(movie) }
//            }
//            .map {
//                LatestMovieRepoOutputModel.Success(it, list) as LatestMovieRepoOutputModel
//            }
//            .mergeWith(updateObs)
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