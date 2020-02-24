package com.worldsnas.domain.di

import com.squareup.sqldelight.db.SqlDriver
import com.worldsnas.db.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DBModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Main =
        Main(driver, Movie.Adapter(DateLongAdapter()))

    @Provides
    @JvmStatic
    fun provideMovieQueries(main: Main): MovieQueries =
        main.movieQueries

    @Provides
    @JvmStatic
    fun provideLatestMovieQueries(main: Main): LatestMovieQueries =
        main.latestMovieQueries

    @Provides
    @JvmStatic
    fun provideLatestGenreQueries(main: Main): GenreQueries =
        main.genreQueries

    @Provides
    @JvmStatic
    fun provideMoviePersister(queries: MovieQueries): MoviePersister =
        MoviePersisterImpl(queries)

    @Provides
    @JvmStatic
    fun provideLatestMoviePersister(queries: LatestMovieQueries, genreQueries: GenreQueries): LatestMoviePersister =
        LatestMoviePersisterImpl(queries, genreQueries)

}