package com.worldsnas.domain.di

import com.squareup.sqldelight.db.SqlDriver
import com.worldsnas.db.Main
import com.worldsnas.db.MoviePersister
import com.worldsnas.db.MoviePersisterImpl
import com.worldsnas.db.MovieQueries
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DBModule {

//    @Binds
//    abstract fun bindMoviePersister(persister: MoviePersisterImpl):
//            MoviePersister

    @Module
    companion object {

        @JvmStatic
        @Provides
        @Singleton
        fun provideDatabase(driver: SqlDriver): Main =
            Main(driver)

        @Provides
        @JvmStatic
        fun provideMovieQueries(main: Main): MovieQueries =
            main.movieQueries

        @Provides
        @JvmStatic
        fun provideMoviePersister(queries: MovieQueries): MoviePersister =
            MoviePersisterImpl(queries)
    }
}