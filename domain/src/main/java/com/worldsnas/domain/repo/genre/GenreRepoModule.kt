package com.worldsnas.domain.repo.genre

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class GenreRepoModule {


    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideGenreAPI(retrofit: Retrofit): GenreAPI =
            retrofit.create()
    }

    @Binds
    abstract fun bindRepo(
        repo: MovieGenreRepoImpl
    ): MovieGenreRepo
}