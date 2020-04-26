package com.worldsnas.domain.repo.home

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.domain.repo.home.latest.*
import com.worldsnas.domain.repo.home.trending.*
import com.worldsnas.panther.Fetcher
import com.worldsnas.panther.Persister
import com.worldsnas.panther.RFetcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class HomeRepoModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRetrofit(retrofit: Retrofit) = retrofit.create<HomeAPI>()
    }

    @Binds
    abstract fun bindLatestFetcher(fetcher: LatestMovieFetcher):
            Fetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>

    @Binds
    abstract fun bindTrendingFetcher(fetcher: TrendingFetcher):
            RFetcher<Int, ResultsServerModel<MovieServerModel>>

    @Binds
    abstract fun bindLatestMovieRepo(repo: LatestMovieRepoImpl):
            LatestMovieRepo

    @Binds
    abstract fun bindTrendingRepo(repo: TrendingRepoImpl):
            TrendingRepo
}