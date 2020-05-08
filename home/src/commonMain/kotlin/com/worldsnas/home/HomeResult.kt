package com.worldsnas.home

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.MviResult
import com.worldsnas.home.model.Movie

sealed class HomeResult : MviResult {
    object Loading : HomeResult()
    object LastStable : HomeResult()
    class Error(val err : BaseState.ErrorState) : HomeResult()

    class LatestMovies(val movies: List<Movie>) : HomeResult()
    class TrendingMovies(val movies: List<Movie>) : HomeResult()
}