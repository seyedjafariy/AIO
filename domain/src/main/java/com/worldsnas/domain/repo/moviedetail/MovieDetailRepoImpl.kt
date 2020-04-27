package com.worldsnas.domain.repo.moviedetail

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.worldsnas.core.ErrorHolder
import com.worldsnas.core.Mapper
import com.worldsnas.core.listMerge
import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.helpers.errorHandler
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isBodyNotEmpty
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPI
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject

interface MovieDetailRepo {

    fun getMovieDetail(param: MovieDetailRepoParamModel): Flow<Either<ErrorHolder, MovieRepoModel>>

    fun getCached(): Single<MovieDetailRepoOutPutModel>
}

private const val DETAIL_RESPONSE_APPEND : String = "videos,images,reviews,similar,recommendations,credits,translations,external_ids"

class MovieDetailRepoImpl @Inject constructor(
    private val api: MovieDetailAPI,
    private val movieMapper: Mapper<MovieServerModel, MovieRepoModel>,
    private val movieDbRepoMapper: Mapper<Movie, MovieRepoModel>,
    private val movieRepoDbMapper: Mapper<MovieRepoModel, Movie>,
    private val genreRepoDbMapper: Mapper<GenreRepoModel, Genre>,
    private val completeMovieRepoMapper: Mapper<CompleteMovie, MovieRepoModel>,
    private val moviePersister: MoviePersister
) : MovieDetailRepo {

    private var movie: MovieRepoModel? = null

    override fun getMovieDetail(param: MovieDetailRepoParamModel): Flow<Either<ErrorHolder, MovieRepoModel>> =
        moviePersister.getMovie(param.movieID)
            .take(1)
            .map {
                movieDbRepoMapper.map(it)
            }
            .onEach{
                movie = it
            }
            .listMerge(
                {
                    fetchAndSave(param.movieID)
                },
                {
                    map {
                        it.right()
                    }
                }
            )

    private fun fetchAndSave(movieId: Long): Flow<Either<ErrorHolder, MovieRepoModel>> =
        internalFetch(movieId)
            .listMerge(
                {
                    errorLeft()
                },
                {
                    parseAndSave()
                }
            )

    private fun Flow<Response<MovieServerModel>>.errorLeft() =
        filter { serverFirstPageResponse ->
            serverFirstPageResponse.isNotSuccessful || serverFirstPageResponse.body() == null
        }.map { serverFirstPageResponse ->
            serverFirstPageResponse.getErrorRepoModel().left()
        }

    private fun Flow<Response<MovieServerModel>>.parseAndSave() =
        filter { serverMovieResponse ->
            serverMovieResponse.isSuccessful && serverMovieResponse.isBodyNotEmpty
        }.map { serverMovieResponse ->
            movieMapper.map(serverMovieResponse.body()!!)
        }
            .saveToDb()
            .flatMapConcat {
                moviePersister.addToMovie(
                    moviePersister
                        .getMovie(it.id)
                        .take(1),
                    genres = true,
                    similars = true,
                    recomendations = true
                )
            }.map { completeMovie ->
                completeMovieRepoMapper.map(completeMovie)
            }.onEach {
                movie = it
            }.map {
                it.right()
            }

    private fun Flow<MovieRepoModel>.saveToDb() = onEach { movie ->
        moviePersister.insertMovies(listOf(
            CompleteMovie(
                movieRepoDbMapper.map(movie),
                movie.genres.map {
                    genreRepoDbMapper.map(it)
                },
                movie.similar.map {
                    movieRepoDbMapper.map(it)
                },
                movie.recommendations.map {
                    movieRepoDbMapper.map(it)
                }
            )
        ))
    }


    override fun getCached(): Single<MovieDetailRepoOutPutModel> =
        Single.just(
            MovieDetailRepoOutPutModel.Cached(
                movie
            )
        )

    private fun internalFetch(movieId : Long) =
        flow {
            emit(
                api
                    .getMovie(
                        movieId,
                        DETAIL_RESPONSE_APPEND
                    )
            )
        }.errorHandler()
            .flowOn(Dispatchers.IO)
}