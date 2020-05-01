package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.db.Genre
import com.worldsnas.db.LatestMoviePersister
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.home.HomeAPI
import com.worldsnas.domain.repo.home.HomeAPIImpl
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.latest.LatestMovieRepoImpl
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepoImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
object HomeRepoModule {

    @JvmStatic
    @Provides
    fun provideRetrofit(engine: HttpClientEngine): HomeAPI =
        HomeAPIImpl(engine)

    @JvmStatic
    @Provides
    fun bindLatestMovieRepo(
        movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>,
        latestMoviePersister: LatestMoviePersister,
        moviePersister: MoviePersister,
        movieRepoDBMapper: Mapper<MovieRepoModel, Movie>,
        movieDBRepoMapper: Mapper<Movie, MovieRepoModel>,
        genreRepoDbMapper: Mapper<GenreRepoModel, Genre>,
        api: HomeAPI
    ): LatestMovieRepo =
        LatestMovieRepoImpl(
            movieServerRepoMapper,
            latestMoviePersister,
            moviePersister,
            movieRepoDBMapper,
            movieDBRepoMapper,
            genreRepoDbMapper,
            api
        )

    @JvmStatic
    @Provides
    fun bindTrendingRepo(
        api: HomeAPI,
        serverRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
    ): TrendingRepo =
        TrendingRepoImpl(api, serverRepoMapper)
}