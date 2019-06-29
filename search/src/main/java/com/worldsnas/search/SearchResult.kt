package com.worldsnas.search

import com.worldsnas.base.BaseState
import com.worldsnas.mvi.MviResult
import com.worldsnas.search.model.MovieUIModel

sealed class SearchResult : MviResult {
    object LastStable : SearchResult()
    class Error(val err : BaseState.ErrorState) : SearchResult()
    class Result(val movies : List<MovieUIModel>) : SearchResult()
}