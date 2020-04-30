package com.worldsnas.domain.repo.genre

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
abstract class GenreRepoModule {


    @Module
    companion object {

        @JvmStatic
        @Provides
        fun provideGenreAPI(engine: HttpClientEngine): GenreAPI =
            GenreAPIImpl(engine)
    }

    @Binds
    abstract fun bindRepo(
        repo: MovieGenreRepoImpl
    ): MovieGenreRepo
}