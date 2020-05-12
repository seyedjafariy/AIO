package com.worldsnas.search

import com.worldsnas.core.mvi.BasePresenter
import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.MviProcessor

class SearchPresenter(
    processor: MviProcessor<SearchIntent, SearchResult>
) : BasePresenter<SearchIntent, SearchState, SearchResult>(
    processor,
    SearchState.idle()
) {

    override fun reduce(preState: SearchState, result: SearchResult): SearchState =
        when (result) {
            SearchResult.LastStable ->
                preState.copy(
                    base = BaseState.stable()
                )
            is SearchResult.Error ->
                preState.copy(
                    base = BaseState.withError(result.err)
                )
            is SearchResult.Result ->
                preState.copy(
                    base = BaseState.stable(),
                    movies = result.movies
                )
        }
}