package com.worldsnas.domain.repo.moviedetail

import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPI
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPIImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module(
    includes = [

    ]
)
abstract class MovieDetailRepoModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideMovieDetailAPI(engine: HttpClientEngine): MovieDetailAPI =
            MovieDetailAPIImpl(engine)
    }

    @Binds
    abstract fun bindMovieDetailRepo(repo: MovieDetailRepoImpl):
            MovieDetailRepo
}