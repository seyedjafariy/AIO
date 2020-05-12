package com.worldsnas.search

import com.worldsnas.core.*
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.core.mvi.BaseProcessor
import com.worldsnas.core.mvi.toErrorState
import com.worldsnas.navigation.navigateTo
import com.worldsnas.search.model.MovieUIModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class SearchProcessor(
    movieSearchRepo: MovieSearchRepo,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>,
    navigator: Navigator
) : BaseProcessor<SearchIntent, SearchResult>() {

    override fun transformers(): List<FlowBlock<SearchIntent, SearchResult>> = listOf(
        newSearchProcessor,
        nextPageProcessor,
        searchMovieClick
    )

    private val newSearchProcessor: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.Search>()
            .map {
                MovieSearchRepoParamModel(
                    it.query,
                    1
                )
            }
            .let(movieSearchRepoParamProcessor)
    }

    private val nextPageProcessor: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.NextPage>()
            .map {
                MovieSearchRepoParamModel(
                    it.query,
                    (it.totalCount / 20) + 1
                )
            }
            .let(movieSearchRepoParamProcessor)
    }
    private val movieSearchRepoParamProcessor: FlowBlock<MovieSearchRepoParamModel, SearchResult> =
        {
            flatMapLatest { param ->
                movieSearchRepo.search(param)
                    .publish(
                        {
                            ofType<Either.Left<ErrorHolder>>()
                                .flatMapLatest {
                                    delayedFlow(
                                        SearchResult.Error(
                                            it.a.toErrorState()
                                        ),
                                        SearchResult.LastStable
                                    )
                                }
                        },
                        {
                            ofType<Either.Right<List<MovieRepoModel>>>()
                                .map {
                                    SearchResult.Result(
                                        it.b.map { movie ->
                                            movieMapper.map(movie)
                                        })
                                }
                        }
                    )
            }
        }

    private val searchMovieClick: FlowBlock<SearchIntent, SearchResult> = {
        ofType<SearchIntent.SearchResultClicked>()
            .map {
                MovieDetailLocalModel(
                    it.movie.id,
                    it.movie.poster,
                    it.movie.cover,
                    it.movie.title,
                    "",
                    it.movie.releaseDate,
                    it.posterTransName,
                    it.releaseTransName,
                    it.titleTransName
                )
            }
            .map {
                Screens.MovieDetail(
                    it,
                    NavigationAnimation.ArcFadeMove(
                        it.posterTransName,
                        it.titleTransName
                    ),
                    NavigationAnimation.ArcFadeMove(
                        it.posterTransName,
                        it.titleTransName
                    )
                )
            }
            .navigateTo(navigator)
    }
}