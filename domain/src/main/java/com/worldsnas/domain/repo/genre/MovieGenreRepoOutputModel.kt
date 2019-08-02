package com.worldsnas.domain.repo.genre

import com.worldsnas.core.ErrorHolder
import com.worldsnas.domain.model.repomodel.GenreRepoModel

sealed class MovieGenreRepoOutputModel {

    class Success(
        val genres : List<GenreRepoModel>
    ) : MovieGenreRepoOutputModel()

    class Error(
        val err : ErrorHolder
    ) : MovieGenreRepoOutputModel()
}