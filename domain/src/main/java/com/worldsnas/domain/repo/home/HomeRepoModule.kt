package com.worldsnas.domain.repo.home

import com.worldsnas.domain.repo.home.latest.*
import com.worldsnas.domain.repo.home.trending.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
abstract class HomeRepoModule {

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRetrofit(engine: HttpClientEngine): HomeAPI =
            HomeAPIImpl(engine)
    }

    @Binds
    abstract fun bindLatestMovieRepo(repo: LatestMovieRepoImpl):
            LatestMovieRepo

    @Binds
    abstract fun bindTrendingRepo(repo: TrendingRepoImpl):
            TrendingRepo
}