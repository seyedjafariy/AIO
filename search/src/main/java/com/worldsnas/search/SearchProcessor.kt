package com.worldsnas.search

import com.worldsnas.domain.repo.search.movie.MovieSearchRepo
import com.worldsnas.mvi.MviProcessor
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchProcessor @Inject constructor(
    movieSearchRepo: MovieSearchRepo
) : MviProcessor<SearchIntent, SearchResult> {

    override val actionProcessor = ObservableTransformer<SearchIntent, SearchResult> { actions ->
        actions.map {
            SearchResult.LastStable
        }
    }
}