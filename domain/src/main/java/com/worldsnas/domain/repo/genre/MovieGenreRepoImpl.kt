package com.worldsnas.domain.repo.genre

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.core.Mapper
import com.worldsnas.domain.helpers.errorHandler
import io.reactivex.Single
import javax.inject.Inject

class MovieGenreRepoImpl @Inject constructor(
    private val api: GenreAPI,
    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel>
) : MovieGenreRepo {

    private var genres: List<GenreRepoModel> = mutableListOf()

    override fun fetchAllGenre(): Single<Either<ErrorHolder, List<GenreRepoModel>>> =
        api.allMovieGenre()
            .errorHandler()
            .eitherError {
                it.genres.map { genre -> genreMapper.map(genre) }
                    .also { repos ->
                        genres = repos
                    }
            }

    override fun cachedGenre(): Single<List<GenreRepoModel>> =
        Single.just(genres)
}