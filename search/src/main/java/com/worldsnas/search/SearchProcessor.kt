package com.worldsnas.search

import com.worldsnas.base.toErrorState
import com.worldsnas.base.delayEvent
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoOutputModel
import com.worldsnas.domain.repo.search.movie.model.MovieSearchRepoParamModel
import com.worldsnas.mvi.MviProcessor
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.panther.Mapper
import com.worldsnas.search.model.MovieUIModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.ofType
import javax.inject.Inject

class SearchProcessor @Inject constructor(
    movieSearchRepo: MovieSearchRepo,
    movieMapper: Mapper<MovieRepoModel, MovieUIModel>,
    navigator: Navigator
) : MviProcessor<SearchIntent, SearchResult> {

    override val actionProcessor = ObservableTransformer<SearchIntent, SearchResult> { actions ->
        actions.publish { publish ->
            Observable.merge(
                publish.ofType<SearchIntent.Search>().compose(newSearchProcessor),
                publish.ofType<SearchIntent.NextPage>().compose(nextPageProcessor),
                publish.ofType<SearchIntent.SearchResultClicked>().compose(searchMovieClick)
            )
        }
    }

    private val newSearchProcessor =
        ObservableTransformer<SearchIntent.Search, SearchResult> { actions ->
            actions
                .map {
                    MovieSearchRepoParamModel(
                        it.query,
                        1
                    )
                }
                .compose(movieSearchRepoParamProcessor)
        }

    private val nextPageProcessor =
        ObservableTransformer<SearchIntent.NextPage, SearchResult> { actions ->
            actions
                .map {
                    MovieSearchRepoParamModel(
                        it.query,
                        (it.totalCount / 20) + 1
                    )
                }
                .compose(movieSearchRepoParamProcessor)
        }

    private val movieSearchRepoParamProcessor =
        ObservableTransformer<MovieSearchRepoParamModel, SearchResult> { actions ->
            actions
                .switchMap { param ->
                    movieSearchRepo.search(param)
                        .toObservable()
                        .publish { publish ->
                            Observable.merge(
                                publish.ofType<MovieSearchRepoOutputModel.Error>()
                                    .switchMap {
                                        delayEvent(
                                            SearchResult.Error(it.err.toErrorState()),
                                            SearchResult.LastStable
                                        )
                                    },
                                publish.ofType<MovieSearchRepoOutputModel.Success>()
                                    .map {
                                        SearchResult.Result(
                                            it.all.map { movie ->
                                                movieMapper.map(movie)
                                            })
                                    }
                            )
                        }
                }
        }

    private val searchMovieClick =
        ObservableTransformer<SearchIntent.SearchResultClicked, SearchResult> { actions ->
            actions
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
                .doOnNext {
                    navigator.goTo(
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
                    )
                }
                .ignoreElements()
                .toObservable()
        }
}