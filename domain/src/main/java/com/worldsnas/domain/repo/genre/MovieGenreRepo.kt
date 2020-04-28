package com.worldsnas.domain.repo.genre

import com.worldsnas.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import kotlinx.coroutines.flow.Flow

interface MovieGenreRepo {

    fun fetchAllGenre(): Flow<Either<ErrorHolder, List<GenreRepoModel>>>
    fun cachedGenre() : Flow<List<GenreRepoModel>>
}