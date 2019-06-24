package com.worldsnas.search

import com.worldsnas.mvi.MviProcessor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchProcessor @Inject constructor(
) : MviProcessor<SearchIntent, SearchResult> {

    override val actionProcessor = ObservableTransformer<SearchIntent, SearchResult> { actions ->
        actions.map {
            SearchResult.LastStable
        }
    }
}