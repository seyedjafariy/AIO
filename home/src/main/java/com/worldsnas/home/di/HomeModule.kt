package com.worldsnas.home.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.home.*
import com.worldsnas.home.mapper.MovieRepoUIMapper
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.core.Mapper
import com.worldsnas.view_component.Movie
import dagger.Binds
import dagger.Module

@Module
abstract class HomeModule {

    @Binds
    abstract fun bindMovieMapper(mapper: MovieRepoUIMapper):
            Mapper<MovieRepoModel, Movie>

    @Binds
    @FeatureScope
    abstract fun bindPresenter(presenter: HomePresenter):
            MviPresenter<HomeIntent, HomeState>

    @Binds
    abstract fun bindProcessor(processor: HomeProcessor):
            MviProcessor<HomeIntent, HomeResult>
}