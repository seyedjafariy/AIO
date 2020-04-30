package com.worldsnas.domain.repo.search

import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepo
import com.worldsnas.domain.repo.search.keywords.SearchKeywordsRepoImpl
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.MovieSearchRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
abstract class SearchRepoModule {
    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideRetrofit(engine: HttpClientEngine): SearchAPI =
            SearchAPIImpl(engine)
    }

    @Binds
    abstract fun bindMovieSearchRepo(repo: MovieSearchRepoImpl):
            MovieSearchRepo

    @Binds
    abstract fun bindSearchKeywordsRepo(repo: SearchKeywordsRepoImpl):
            SearchKeywordsRepo
}