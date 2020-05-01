package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.db.CompleteMovie
import com.worldsnas.db.Genre
import com.worldsnas.db.Movie
import com.worldsnas.db.MoviePersister
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepoImpl
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPI
import com.worldsnas.domain.repo.moviedetail.network.MovieDetailAPIImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
object MovieDetailRepoModule {
    @JvmStatic
    @Provides
    fun provideMovieDetailAPI(engine: HttpClientEngine): MovieDetailAPI =
        MovieDetailAPIImpl(engine)

    @JvmStatic
    @Provides
    fun bindMovieDetailRepo(
        api: MovieDetailAPI,
        movieMapper: Mapper<MovieServerModel, MovieRepoModel>,
        movieDbRepoMapper: Mapper<Movie, MovieRepoModel>,
        movieRepoDbMapper: Mapper<MovieRepoModel, Movie>,
        genreRepoDbMapper: Mapper<GenreRepoModel, Genre>,
        completeMovieRepoMapper: Mapper<CompleteMovie, MovieRepoModel>,
        moviePersister: MoviePersister
    ): MovieDetailRepo = MovieDetailRepoImpl(
        api,
        movieMapper,
        movieDbRepoMapper,
        movieRepoDbMapper,
        genreRepoDbMapper,
        completeMovieRepoMapper,
        moviePersister
    )
}