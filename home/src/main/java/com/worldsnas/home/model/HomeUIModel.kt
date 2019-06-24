package com.worldsnas.home.model

sealed class HomeUIModel {


    data class Slider(val slides: List<MovieUIModel>) : HomeUIModel()

    data class LatestMovie(val movie: MovieUIModel) : HomeUIModel()

}