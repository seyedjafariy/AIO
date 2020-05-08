package com.worldsnas.home

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.BaseViewState
import com.worldsnas.home.model.Movie

data class HomeState(
    override val base: BaseState,
    val sliderMovies: List<Movie>,
    val latest: List<Movie>
) : BaseViewState {
    companion object {
        fun start() = HomeState(
            base = BaseState.stable(),
            sliderMovies = emptyList(),
            latest = emptyList()
        )
    }
}