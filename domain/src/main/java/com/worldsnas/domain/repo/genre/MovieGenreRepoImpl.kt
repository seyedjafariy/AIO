package com.worldsnas.domain.repo.genre

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.helpers.eitherError
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.core.Mapper
import com.worldsnas.core.suspendToFlow
import com.worldsnas.domain.helpers.errorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MovieGenreRepoImpl @Inject constructor(
    private val api: GenreAPI,
    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel>
) : MovieGenreRepo {

    private var genres: List<GenreRepoModel> = mutableListOf()

    override fun fetchAllGenre(): Flow<Either<ErrorHolder, List<GenreRepoModel>>> =
        suspendToFlow{
            api.allMovieGenre()
        }
            .errorHandler()
            .eitherError {
                it.genres.map { genre -> genreMapper.map(genre) }
                    .also { repos ->
                        genres = repos
                    }
            }

    override fun cachedGenre(): Flow<List<GenreRepoModel>> =
        flowOf(genres)
}