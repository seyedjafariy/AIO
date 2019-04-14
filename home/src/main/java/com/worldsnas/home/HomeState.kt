package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.home.view.MovieSliderUIModel

data class HomeState(
    override val base: BaseState,
    val sliderMovies: List<MovieSliderUIModel>,
    val movies: List<MovieUIModel>
) : BaseViewState {
    companion object {
        fun startingState() = HomeState(
            base = BaseState.showError("اصغر"),
            sliderMovies = emptyList(),
            movies = emptyList())
    }
}