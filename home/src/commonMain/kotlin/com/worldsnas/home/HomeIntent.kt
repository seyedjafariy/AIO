package com.worldsnas.home

import com.worldsnas.core.mvi.MviIntent
import com.worldsnas.home.model.Movie

sealed class HomeIntent : MviIntent {
    object Initial : HomeIntent()
    data class NextPage(val page : Int, val totalCount: Int) : HomeIntent()
    class LatestMovieClicked(
        val movie : Movie,
        val posterTransName : String = "",
        val titleTransName : String = "",
        val releaseTransName : String = "") : HomeIntent()
    class SliderClicked(
        val movieId : Long,
        val imgTransName : String = ""
    ) : HomeIntent()
    class SearchClicks(
        val backTransName : String,
        val textTransName : String
    ) : HomeIntent()
}