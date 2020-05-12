package com.worldsnas.search

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.BaseViewState
import com.worldsnas.search.model.MovieUIModel

data class SearchState(
    override val base: BaseState,
    val movies: List<MovieUIModel>
) : BaseViewState {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable(),
                movies = emptyList()
            )
    }
}