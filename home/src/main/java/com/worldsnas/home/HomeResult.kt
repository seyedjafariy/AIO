package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviResult
import com.worldsnas.view_component.Movie

sealed class HomeResult : MviResult {
    object Loading : HomeResult()
    object LastStable : HomeResult()
    class Error(val err : BaseState.ErrorState) : HomeResult()

    class LatestMovies(val movies: List<Movie>) : HomeResult()
    class TrendingMovies(val movies: List<Movie>) : HomeResult()
}