package com.worldsnas.domain.repo.moviedetail

import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPI
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module(
    includes = [

    ]
)
abstract class MovieDetailRepoModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideMovieDetailAPI(retrofit: Retrofit): MovieDetailAPI =
            retrofit.create()
    }

    @Binds
    abstract fun bindMovieDetailRepo(repo: MovieDetailRepoImpl):
            MovieDetailRepo
}