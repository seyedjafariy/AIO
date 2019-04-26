package com.worldsnas.domain.repo.home

import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.entity.TrendingEntity
import com.worldsnas.domain.repo.home.latest.LatestMovieFetcher
import com.worldsnas.domain.repo.home.latest.LatestMoviePersister
import com.worldsnas.domain.repo.home.latest.LatestMoviePersisterKey
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoImpl
import com.worldsnas.domain.repo.home.latest.LatestMovieRequestParam
import com.worldsnas.domain.repo.home.trending.TrendingFetcher
import com.worldsnas.domain.repo.home.trending.TrendingPersister
import com.worldsnas.domain.repo.home.trending.TrendingPersisterKey
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepoImpl
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
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
        RFetcher<LatestMovieRequestParam, ResultsServerModel<MovieServerModel>>

    @Binds
    abstract fun bindTrendingFetcher(fetcher: TrendingFetcher):
        RFetcher<Int, ResultsServerModel<MovieServerModel>>

    @Binds
    abstract fun bindLatestMoviePersister(persister: LatestMoviePersister):
        Persister<LatestMoviePersisterKey, List<@JvmSuppressWildcards MovieEntity>>

    @Binds
    abstract fun bindTrendingPersister(persister: TrendingPersister):
        Persister<TrendingPersisterKey, TrendingEntity>

    @Binds
    abstract fun bindLatestMovieRepo(repo: LatestMovieRepoImpl):
        LatestMovieRepo

    @Binds
    abstract fun bindTrendingRepo(repo: TrendingRepoImpl):
        TrendingRepo
}