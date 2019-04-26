package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState
import com.worldsnas.home.model.MovieUIModel

data class HomeState(
    override val base: BaseState,
    val sliderMovies: List<MovieUIModel>,
    val latest: List<MovieUIModel>
) : BaseViewState {
    companion object {
        fun start() = HomeState(
            base = BaseState.stable(),
            sliderMovies = emptyList(),
            latest = emptyList())
    }
}