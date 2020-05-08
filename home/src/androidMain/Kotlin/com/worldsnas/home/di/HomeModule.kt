package com.worldsnas.home.di

import com.worldsnas.core.Mapper
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.core.mvi.MviProcessor
import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import com.worldsnas.home.*
import com.worldsnas.home.mapper.MovieRepoUIMapper
import com.worldsnas.home.model.Movie
import com.worldsnas.navigation.Navigator
import dagger.Module
import dagger.Provides

@Module
object HomeModule {

    @Provides
    @JvmStatic
    fun provideMovieMapper(): Mapper<MovieRepoModel, Movie> =
        MovieRepoUIMapper()

    @JvmStatic
    @Provides
    @FeatureScope
    fun providePresenter(
        processor: MviProcessor<HomeIntent, HomeResult>
    ): MviPresenter<HomeIntent, HomeState> = HomePresenter(
        processor
    )

    @JvmStatic
    @Provides
    fun provideProcessor(
        navigator: Navigator,
        latestRepo: LatestMovieRepo,
        trendingRepo: TrendingRepo,
        movieMapper: Mapper<MovieRepoModel, Movie>
    ): MviProcessor<HomeIntent, HomeResult> = HomeProcessor(
        navigator, latestRepo, trendingRepo, movieMapper
    )
}