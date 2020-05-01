package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.domain.repo.genre.GenreAPI
import com.worldsnas.domain.repo.genre.GenreAPIImpl
import com.worldsnas.domain.repo.genre.MovieGenreRepo
import com.worldsnas.domain.repo.genre.MovieGenreRepoImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
object GenreRepoModule {
    @JvmStatic
    @Provides
    fun provideGenreAPI(engine: HttpClientEngine): GenreAPI =
        GenreAPIImpl(engine)

    @JvmStatic
    @Provides
    fun bindRepo(
        api: GenreAPI,
        genreMapper: Mapper<GenreServerModel, GenreRepoModel>
    ): MovieGenreRepo =
        MovieGenreRepoImpl(
            api, genreMapper
        )
}