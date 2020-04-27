package com.worldsnas.domain.repo.home

import com.worldsnas.domain.repo.home.latest.*
import com.worldsnas.domain.repo.home.trending.*
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
    abstract fun bindLatestMovieRepo(repo: LatestMovieRepoImpl):
            LatestMovieRepo

    @Binds
    abstract fun bindTrendingRepo(repo: TrendingRepoImpl):
            TrendingRepo
}