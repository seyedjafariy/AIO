package com.worldsnas.moviedetail.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.moviedetail.*
import com.worldsnas.moviedetail.mapper.GenreRepoUIMapper
import com.worldsnas.moviedetail.mapper.MovieRepoUIMapper
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailModule {

    @Binds
    abstract fun bindProcessor(processor: MovieDetailProcessor):
            MviProcessor<MovieDetailIntent, MovieDetailResult>

    @FeatureScope
    @Binds
    abstract fun bindPresenter(presenter: MovieDetailPresenter):
            MviPresenter<MovieDetailIntent, MovieDetailState>

    @Binds
    abstract fun bindGenreRepoUIMapper(mapper: GenreRepoUIMapper):
            Mapper<GenreRepoModel, GenreUIModel>

    @Binds
    abstract fun bindMovieRepoUIMapper(mapper: MovieRepoUIMapper):
            Mapper<MovieRepoModel, MovieUIModel>

}