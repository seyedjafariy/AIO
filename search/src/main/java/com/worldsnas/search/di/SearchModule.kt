package com.worldsnas.search.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.core.Mapper
import com.worldsnas.search.*
import com.worldsnas.search.mapper.MovieRepoUIMapper
import com.worldsnas.search.model.MovieUIModel
import dagger.Binds
import dagger.Module

@Module
abstract class SearchModule {

    @Binds
    abstract fun bindProcessor(processor: SearchProcessor):
            MviProcessor<SearchIntent, SearchResult>

    @FeatureScope
    @Binds
    abstract fun bindPresenter(presenter: SearchPresenter):
            MviPresenter<SearchIntent, SearchState>

    @Binds
    abstract fun bindMovieRepoUIMapper(mapper: MovieRepoUIMapper):
            Mapper<MovieRepoModel, MovieUIModel>

}