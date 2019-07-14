package com.worldsnas.domain.repo.genre

import io.reactivex.Single

interface MovieGenreRepo {

    fun fetchAllGenre(): Single<MovieGenreRepoOutputModel>

}