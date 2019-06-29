package com.worldsnas.domain.repo.search

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.search.movie.MovieSearchFetcher
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRequestParam
import com.worldsnas.panther.Fetcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class SearchRepoModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRetrofit(retrofit: Retrofit) = retrofit.create<SearchAPI>()
    }


    @Binds
    abstract fun bindMovieSearchRepo(repo: MovieSearchRepoImpl) :
            MovieSearchRepo

    @Binds
    abstract fun bindMovieSearchFetcher(fetcher : MovieSearchFetcher) :
            Fetcher<MovieSearchRequestParam, Response<ResultsServerModel<MovieServerModel>>>

}