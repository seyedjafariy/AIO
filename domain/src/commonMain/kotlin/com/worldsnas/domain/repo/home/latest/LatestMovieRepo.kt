package com.worldsnas.domain.repo.home.latest

import com.worldsnas.core.*
import com.worldsnas.db.*
import com.worldsnas.domain.helpers.Response
import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.toStringDate
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import io.islandtime.Instant
import io.islandtime.clock.now
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

interface LatestMovieRepo {
    fun receiveAndUpdate(param: PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun fetch(param: LatestMovieRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun memCache(): Flow<List<MovieRepoModel>>
}

private const val MOVIE_PAGE_SIZE = 20

class LatestMovieRepoImpl(
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
    private val latestMoviePersister: LatestMoviePersister,
    private val moviePersister: MoviePersister,
    private val movieRepoDBMapper: Mapper<MovieRepoModel, Movie>,
    private val movieDBRepoMapper: Mapper<Movie, MovieRepoModel>,
    private val genreRepoDbMapper: Mapper<GenreRepoModel, Genre>,
    private val api: HomeAPI
) : LatestMovieRepo {

    var list: MutableList<MovieRepoModel> = mutableListOf()

    override fun memCache(): Flow<List<MovieRepoModel>> =
        flowOf(list)

    override fun receiveAndUpdate(param: PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        when (param) {
            PageModel.First -> loadFirstPage()
            is PageModel.NextPage -> loadNextPage()
        }

    private fun loadFirstPage() =
        latestMoviePersister.observeSimpleMovies()
            .take(1)
            .map { movies ->
                movies.map { movieDBRepoMapper.map(it) }
            }
            .listMerge(
                {
                    onEach { list = it.toMutableList() }
                        .map { it.right() }
                },
                {
                    fetchAndSave(1, true)
                }
            )

    private fun fetchAndSave(
        page: Int,
        validateDb: Boolean
    ): Flow<Either<ErrorHolder, MutableList<MovieRepoModel>>> =
        internalFetch(page)
            .listMerge(
                {
                    errorLeft()
                },
                {
                    parseAndSave(validateDb)
                }
            )

    private fun Flow<Response<ResultsServerModel<MovieServerModel>>>.errorLeft() =
        filter { serverFirstPageResponse ->
            serverFirstPageResponse.isNotSuccessful
        }.map { serverFirstPageResponse ->
            serverFirstPageResponse.getErrorRepoModel().left()
        }

    private fun Flow<Response<ResultsServerModel<MovieServerModel>>>.parseAndSave(validateDb: Boolean): Flow<Either<Nothing, MutableList<MovieRepoModel>>> =
        filter { serverFirstPageResponse ->
            serverFirstPageResponse.isSuccessful
        }.map { serverFirstPageResponse ->
            serverFirstPageResponse as Response.Success<ResultsServerModel<MovieServerModel>>
            serverFirstPageResponse.data.list.map {
                movieServerRepoMapper.map(it)
            }
        }
            .validateDb(validateDb)
            .saveToDb()
            .flatMapConcat {
                latestMoviePersister
                    .observeSimpleMovies()
                    .take(1)
            }.map { movies ->
                movies.map {
                    movieDBRepoMapper.map(it)
                }
            }.onEach {
                list = it.toMutableList()
            }.map {
                list.right()
            }

    private fun Flow<List<MovieRepoModel>>.validateDb(validateDb: Boolean) =
        onEach { serverFirstPage: List<MovieRepoModel> ->
            if (!validateDb) {
                return@onEach
            }

            val dbValid = serverFirstPage.map { it.id }
                .asFlow()
                .toListFlow()
                .flatMapConcat {
                    moviePersister.findAny(it)
                }
                .map {
                    it != null
                }
                .first()

            if (!dbValid) {
                latestMoviePersister.clearMovies()
                list.clear()
            }
        }

    private fun Flow<List<MovieRepoModel>>.saveToDb() = onEach { networkResult ->
        latestMoviePersister.insertMovies(networkResult.map { mainMovie ->
            CompleteMovie(
                movieRepoDBMapper.map(mainMovie),
                genres = mainMovie.genres.map { genreRepoDbMapper.map(it) },
                similars = mainMovie.similar.map { movieRepoDBMapper.map(it) },
                recommended = mainMovie.recommendations.map { movieRepoDBMapper.map(it) }
            )
        })
    }

    private fun loadNextPage(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        latestMoviePersister.movieCount()
            .take(1)
            .map {
                (it / MOVIE_PAGE_SIZE) + 1
            }
            .filter { it > 1 }
            .flowOn(Dispatchers.Default)
            .flatMapConcat {
                fetchAndSave(it.toInt(), false)
            }

    override fun fetch(param: LatestMovieRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        fetchAndSave(param.page, false)

    private fun internalFetch(page: Int) =
        flow {
            emit(
                api.getLatestMovie(
                    Instant.now().toStringDate(),
                    page
                )
            )
        }.errorHandler()
            .flowOn(Dispatchers.IO)
}