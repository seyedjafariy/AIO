package com.worldsnas.home

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.mvi.MviResult
import com.worldsnas.view_component.Movie

sealed class HomeResult : MviResult {
    object Loading : HomeResult()
    object LastStable : HomeResult()
    class Error(val err : BaseState.ErrorState) : HomeResult()

    class LatestMovies(val movies: List<Movie>) : HomeResult()
    class TrendingMovies(val movies: List<Movie>) : HomeResult()
}