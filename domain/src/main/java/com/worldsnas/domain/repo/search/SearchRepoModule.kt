package com.worldsnas.domain.repo.search

import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import dagger.Binds
import dagger.Module

@Module
abstract class SearchRepoModule {
    @Binds
    abstract fun bindSearchRepo(repo: MovieSearchRepoImpl) :
            MovieSearchRepo

}