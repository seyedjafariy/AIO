package com.worldsnas.moviedetail.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.moviedetail.*
import com.worldsnas.moviedetail.mapper.GenreRepoUIMapper
import com.worldsnas.moviedetail.mapper.MovieRepoUIMapper
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.core.Mapper
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.core.mvi.MviProcessor
import com.worldsnas.domain.repo.moviedetail.MovieDetailRepo
import com.worldsnas.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
object MovieDetailModule {

    @JvmStatic
    @Provides
    fun provideProcessor(
        repo: MovieDetailRepo,
        navigator: Navigator,
        genreMapper: Mapper<GenreRepoModel, GenreUIModel>,
        movieMapper: Mapper<MovieRepoModel, MovieUIModel>
    ): MviProcessor<MovieDetailIntent, MovieDetailResult> =
        MovieDetailProcessor(
            repo, navigator, genreMapper, movieMapper
        )

    @JvmStatic
    @Provides
    fun provideGenreRepoUIMapper(): Mapper<GenreRepoModel, GenreUIModel> =
        GenreRepoUIMapper()

    @FeatureScope
    @Provides
    @JvmStatic
    fun providePresenter(
        processor : MviProcessor<MovieDetailIntent, MovieDetailResult>
    ): MviPresenter<MovieDetailIntent, MovieDetailState> =
        MovieDetailPresenter(processor)

    @Provides
    @JvmStatic
    fun bindMovieRepoUIMapper(): Mapper<MovieRepoModel, MovieUIModel> =
        MovieRepoUIMapper()

}