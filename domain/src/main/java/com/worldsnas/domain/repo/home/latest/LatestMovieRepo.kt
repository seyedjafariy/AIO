package com.worldsnas.domain.repo.home.latest

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.listMerge
import com.worldsnas.core.toListFlow
import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.LatestMoviePersister
import com.worldsnas.db.Movie
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isBodyNotEmpty
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.PageModel
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.system.measureTimeMillis

interface LatestMovieRepo {
    fun receiveAndUpdate(param: PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun fetch(param: LatestMovieRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>>
    fun memCache(): Single<LatestMovieRepoOutputModel.Success>
}

private const val MOVIE_PAGE_SIZE = 20

class LatestMovieRepoImpl @Inject constructor(
    private val fetcher: Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>,
    private val movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
    private val moviePersister: LatestMoviePersister,
    private val movieRepoDBMapper: Mapper<MovieRepoModel, Movie>,
    private val movieDBRepoMapper: Mapper<Movie, MovieRepoModel>,
    private val genreRepoDbMapper: Mapper<GenreRepoModel, Genre>
) : LatestMovieRepo {

    var list: MutableList<MovieRepoModel> = mutableListOf()

    override fun memCache(): Single<LatestMovieRepoOutputModel.Success> =
        Single.just(
            LatestMovieRepoOutputModel.Success(
                emptyList(),
                list
            )
        )

    override fun receiveAndUpdate(param: PageModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        when (param) {
            PageModel.First -> loadFirstPage()
            is PageModel.NextPage -> loadNextPage()
        }

    private fun loadFirstPage() =
        moviePersister.observeSimpleMovies()
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
                    fetchAndSave(LatestMovieRequestParam(Date(), 1), true)
                }
            )

    private fun fetchAndSave(param: LatestMovieRequestParam, validateDb: Boolean) =
        flow {
            emit(fetcher.fetch(param))
        }.listMerge(
            {
                errorLeft()
            },
            {
                parseAndSave(validateDb)
            }
        )

    private fun Flow<Response<ResultsServerModel<MovieServerModel>>>.errorLeft() =
        filter { serverFirstPageResponse ->
            serverFirstPageResponse.isNotSuccessful || serverFirstPageResponse.body() == null
        }.map { serverFirstPageResponse ->
            serverFirstPageResponse.getErrorRepoModel().left()
        }

    private fun Flow<Response<ResultsServerModel<MovieServerModel>>>.parseAndSave(validateDb: Boolean) =
        filter { serverFirstPageResponse ->
            serverFirstPageResponse.isSuccessful && serverFirstPageResponse.isBodyNotEmpty
        }.map { serverFirstPageResponse ->
            serverFirstPageResponse.body()!!.list.map {
                movieServerRepoMapper.map(it)
            }
        }
            .validateDb(validateDb)
            .saveToDb()
            .flatMapConcat {
                moviePersister
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
                moviePersister.clearMovies()
                list.clear()
            }
        }

    private fun Flow<List<MovieRepoModel>>.saveToDb() = onEach { networkResult ->
        val timeSpent = measureTimeMillis {
            moviePersister.insertMovies(networkResult.map { mainMovie ->
                CompleteMovie(
                    movieRepoDBMapper.map(mainMovie),
                    genres = mainMovie.genres.map { genreRepoDbMapper.map(it) },
                    similars = mainMovie.similar.map { movieRepoDBMapper.map(it) },
                    recommended = mainMovie.recommendations.map { movieRepoDBMapper.map(it) }
                )
            })
        }

        Timber.d("timeSpent to save 20 movies to db= $timeSpent")
    }

    private fun loadNextPage(): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        moviePersister.movieCount()
            .take(1)
            .map {
                (it / MOVIE_PAGE_SIZE) + 1
            }
            .filter { it > 1 }
            .map { page ->
                LatestMovieRequestParam(Date(), page.toInt())
            }
            .flowOn(Dispatchers.Default)
            .flatMapConcat {
                fetchAndSave(it, false)
            }

    override fun fetch(param: LatestMovieRepoParamModel): Flow<Either<ErrorHolder, List<MovieRepoModel>>> =
        fetchAndSave(LatestMovieRequestParam(Date(), param.page), false)
}