package com.worldsnas.search.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.search.*
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

}