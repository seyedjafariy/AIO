package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.listMerge
import com.worldsnas.db.LatestMoviePersister
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isEmptyBody
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.PageModel
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asPublisher
import retrofit2.Response
import javax.inject.Inject

class LatestMovieRepoImpl @Inject constructor(
    private val oldFetcher: RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val fetcher: Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val latestMoviePersister: Persister<LatestMoviePersisterKey, List<@JvmSuppressWildcards MovieEntity>>,
    private val movieServerMapper: Mapper<MovieServerModel, MovieEntity>,
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
    private val movieEntityMapper: Mapper<MovieEntity, MovieRepoModel>,
    private val moviePersister: LatestMoviePersister,
    private val movieRepoDBMapper: Mapper<MovieRepoModel, Movie>,
    private val movieDBRepoMapper: Mapper<Movie, MovieRepoModel>
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

    override fun receiveAndUpdate(param: PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        when (param) {
            PageModel.First -> loadFirstPage()
            is PageModel.NextPage -> loadNextPage()
        }

    private fun loadFirstPage(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> = flow {
        val entireDb: List<MovieRepoModel> =
            moviePersister.observeMovies()
                .take(1)
                .toList()
                .flatten()
                .map {
                    movieDBRepoMapper.map(it)
                }

        emit(entireDb.right())

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
            movieRepoDBMapper.map(it)
        })
    }

    private fun loadNextPage(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        moviePersister.movieCount()
            .map {
                it / MOVIE_PAGE_SIZE
            }
            .map { page ->
                fetcher.fetch(LatestMovieRequestParam(page.toInt()))
            }
            .listMerge { responseFlow ->
                listOf(
                    responseFailed(responseFlow),
                    responseSuccess(responseFlow)
                )
            }
            .flowOn(Dispatchers.Default)

    private fun responseFailed(responseFlow: Flow<Response<ResultsServerModel<MovieServerModel>>>) =
        responseFlow
            .filter { response ->
                response.isNotSuccessful || response.isEmptyBody || response.body()!!.list.isEmpty()
            }
            .map { response ->
                response.getErrorRepoModel().left()
            }


    private fun responseSuccess(responseFlow: Flow<Response<ResultsServerModel<MovieServerModel>>>) =
        responseFlow
            .filter { response ->
                response.body()?.list?.isNotEmpty() ?: false
            }
            .map { response ->
                response.body()?.list ?: emptyList()
            }
            .map { serverList ->
                serverList.map {
                    movieServerRepoMapper.map(it)
                }
            }
            .onEach { repoList ->
                moviePersister.insertMovies(repoList.map { movieRepoDBMapper.map(it) })
            }
            .map {
                it.right()
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

private const val MOVIE_PAGE_SIZE = 20
