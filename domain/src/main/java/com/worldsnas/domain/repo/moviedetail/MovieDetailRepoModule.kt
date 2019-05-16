package com.worldsnas.domain.repo.moviedetail

import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.model.MovieDetailRequestModel
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPI
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailFetcher
import com.worldsnas.panther.Fetcher
import dagger.Binds
import dagger.Module
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class MovieDetailRepoModule {

    @Module
    companion object {

        @JvmStatic
        fun provideMovieDetailAPI(retrofit: Retrofit) =
            retrofit.create<MovieDetailAPI>()
    }

    @Binds
    abstract fun bindMovieDetailRepo(repo: MovieDetailRepoImpl):
            MovieDetailRepo

    @Binds
    abstract fun bindMovieDetailFetcher(repo: MovieDetailFetcher):
            Fetcher<MovieDetailRequestModel, Response<MovieServerModel>>
}