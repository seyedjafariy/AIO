package com.worldsnas.domain.repo.moviedetail

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.R
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoOutPutModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRepoParamModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRequestModel
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Mapper
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class MovieDetailRepoImpl @Inject constructor(
    private val fetcher: Fetcher<@JvmSuppressWildcards MovieDetailRequestModel, Response<MovieServerModel>>,
    private val movieMapper: Mapper<MovieServerModel, MovieRepoModel>
) : MovieDetailRepo {

    override fun getMovieDetail(param: MovieDetailRepoParamModel): Single<MovieDetailRepoOutPutModel> =
        fetcher
            .fetch(
                MovieDetailRequestModel(
                    param.movieID
                )
            )
            .map {
                if (it.isNotSuccessful) {
                    return@map MovieDetailRepoOutPutModel.Error(it.getErrorRepoModel())
                }

                val body = it.body()
                    ?: return@map MovieDetailRepoOutPutModel
                        .Error(
                            ErrorHolder.Res(
                                R.string.error_no_item_received,
                                it.code()
                            )
                        )

                MovieDetailRepoOutPutModel.Success(
                    movieMapper.map(body)
                )
            }


}