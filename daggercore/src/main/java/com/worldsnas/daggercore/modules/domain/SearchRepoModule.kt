package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.KeywordRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.servermodels.KeywordServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.repo.search.SearchAPI
import com.worldsnas.domain.repo.search.SearchAPIImpl
import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepo
import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepoImpl
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
object SearchRepoModule {

    @JvmStatic
    @Provides
    fun provideRetrofit(engine: HttpClientEngine): SearchAPI =
        SearchAPIImpl(engine)

    @JvmStatic
    @Provides
    fun bindMovieSearchRepo(
        api: SearchAPI,
        movieServerRepoMapper: Mapper<MovieServerModel, MovieRepoModel>
    ): MovieSearchRepo = MovieSearchRepoImpl(
        api, movieServerRepoMapper
    )

    @JvmStatic
    @Provides
    fun bindSearchKeywordsRepo(
        api: SearchAPI,
        mapper: Mapper<KeywordServerModel, KeywordRepoModel>
    ): SearchKeywordsRepo = SearchKeywordsRepoImpl(
        api, mapper
    )
}