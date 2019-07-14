package com.worldsnas.domain.repo.genre

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.R
import com.worldsnas.domain.helpers.getErrorRepoModel
import com.worldsnas.domain.helpers.isNotSuccessful
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.panther.Mapper
import com.worldsnas.panther.RFetcher
import io.reactivex.Single
import javax.inject.Inject

class MovieGenreRepoImpl @Inject constructor(
    private val movieGenreFetcher: RFetcher<Unit, ArrayList<GenreServerModel>>,
    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel>
) : MovieGenreRepo {

    override fun fetchAllGenre(): Single<MovieGenreRepoOutputModel> =
        movieGenreFetcher.fetch(Unit)
            .map {
                if (it.isNotSuccessful) {
                    return@map MovieGenreRepoOutputModel.Error(it.getErrorRepoModel())
                }

                val body = it.body()
                    ?: return@map MovieGenreRepoOutputModel
                        .Error(
                            ErrorHolder.Res(
                                R.string.error_no_item_received,
                                it.code()
                            )
                        )

                MovieGenreRepoOutputModel.Success(
                    body.map { genre -> genreMapper.map(genre) }
                )
            }
}