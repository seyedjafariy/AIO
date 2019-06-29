package com.worldsnas.domain.repo.search

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.movie.MovieSearchFetcher
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRequestParam
import com.worldsnas.panther.RFetcher
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
    abstract fun bindMovieSearchFetcher(fetcher: MovieSearchFetcher):
            RFetcher<MovieSearchRequestParam, ResultsServerModel<MovieServerModel>>
}