package com.worldsnas.search

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.base.BaseViewStateOld
import com.worldsnas.search.model.MovieUIModel

data class SearchState(
    override val base: BaseState,
    val movies: List<MovieUIModel>
) : BaseViewStateOld {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable(),
                movies = emptyList()
            )
    }
}