package com.worldsnas.home.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.repomodel.MovieRepoModel
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomePresenter
import com.worldsnas.home.HomeProcessor
import com.worldsnas.home.HomeResult
import com.worldsnas.home.HomeState
import com.worldsnas.home.mapper.MovieRepoUIMapper
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    @FeatureScope
    abstract fun bindMovieMapper(mapper: MovieRepoUIMapper):
        Mapper<MovieRepoModel, MovieUIModel>

    @Binds
    @FeatureScope
    abstract fun bindPresenter(presenter: HomePresenter):
        MviPresenter<HomeIntent, HomeState>

    @Binds
    @FeatureScope
    abstract fun bindProcessor(processor: HomeProcessor):
        MviProcessor<HomeIntent, HomeResult>
}