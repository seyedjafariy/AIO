package com.worldsnas.domain.repo.home

import com.worldsnas.domain.repo.home.discover.LatestMovieFetcher
import com.worldsnas.domain.repo.home.trending.TrendingFetcher
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
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
        @Provides
        fun provideRetrofit(retrofit: Retrofit) = retrofit.create<HomeAPI>()
    }

    @Binds
    abstract fun bindLatestFetcher(fetcher: LatestMovieFetcher):
        RFetcher<Int, ResultsServerModel<MovieServerModel>>

    @Binds
    abstract fun bindTrendingFetcher(fetcher: TrendingFetcher):
        RFetcher<Unit, ResultsServerModel<MovieServerModel>>
}