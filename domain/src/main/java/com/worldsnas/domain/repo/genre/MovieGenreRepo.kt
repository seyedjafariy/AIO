package com.worldsnas.domain.repo.genre

import arrow.core.Either
import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import io.reactivex.Single

interface MovieGenreRepo {

    fun fetchAllGenre(): Single<Either<ErrorHolder, List<GenreRepoModel>>>
    fun cachedGenre() : Single<List<GenreRepoModel>>
}