package com.worldsnas.moviedetail.di

import com.worldsnas.moviedetail.*
import com.worldsnas.mvi.MviPresenter
import com.worldsnas.mvi.MviProcessor
import dagger.Binds
import dagger.Module

@Module
abstract class MovieDetailModule {

    @Binds
    abstract fun bindProcessor(processor: MovieDetailProcessor) :
            MviProcessor<MovieDetailIntent, MovieDetailResult>

    @Binds
    abstract fun bindPresenter(presenter: MovieDetailPresenter):
            MviPresenter<MovieDetailIntent, MovieDetailState>

}