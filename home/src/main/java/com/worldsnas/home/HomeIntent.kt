package com.worldsnas.home

import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviIntent

sealed class HomeIntent : MviIntent {
    object Initial : HomeIntent()
    data class NextPage(val page : Int, val totalCount: Int) : HomeIntent()
    class LatestMovieClicked(
        val movie : MovieUIModel,
        val posterTransName : String = "",
        val titleTransName : String = "",
        val releaseTransName : String = "") : HomeIntent()
    class SliderClicked(val movieId : Long) : HomeIntent()
}