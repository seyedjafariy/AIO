package com.worldsnas.search.di

import com.worldsnas.daggercore.scope.FeatureScope
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.core.Mapper
import com.worldsnas.core.mvi.MviPresenter
import com.worldsnas.core.mvi.MviProcessor
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.navigation.Navigator
import com.worldsnas.search.*
import com.worldsnas.search.mapper.MovieRepoUIMapper
import com.worldsnas.search.model.MovieUIModel
import dagger.Module
import dagger.Provides

@Module
object SearchModule {

    @Provides
    @JvmStatic
    fun bindProcessor(
        movieSearchRepo: MovieSearchRepo,
        movieMapper: Mapper<MovieRepoModel, MovieUIModel>,
        navigator: Navigator
    ): MviProcessor<SearchIntent, SearchResult> =
        SearchProcessor(
            movieSearchRepo, movieMapper, navigator
        )

    @FeatureScope
    @Provides
    @JvmStatic
    fun bindPresenter(
        processor: MviProcessor<SearchIntent, SearchResult>
    ): MviPresenter<SearchIntent, SearchState> = SearchPresenter(
        processor
    )

    @Provides
    @JvmStatic
    fun bindMovieRepoUIMapper(): Mapper<MovieRepoModel, MovieUIModel> = MovieRepoUIMapper()

}