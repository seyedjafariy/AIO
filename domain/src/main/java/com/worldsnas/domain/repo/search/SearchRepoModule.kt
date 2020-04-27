package com.worldsnas.domain.repo.search

import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepo
import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepoImpl
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class SearchRepoModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRetrofit(retrofit: Retrofit): SearchAPI =
            retrofit.create()
    }

    @Binds
    abstract fun bindMovieSearchRepo(repo: MovieSearchRepoImpl):
            MovieSearchRepo

    @Binds
    abstract fun bindSearchKeywordsRepo(repo: SearchKeywordsRepoImpl):
            SearchKeywordsRepo
}