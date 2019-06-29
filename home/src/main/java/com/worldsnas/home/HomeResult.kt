package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.home.model.MovieUIModel
import com.worldsnas.mvi.MviResult

sealed class HomeResult : MviResult {
    object Loading : HomeResult()
    object LastStable : HomeResult()
    class Error(val err : BaseState.ErrorState) : HomeResult()

    class LatestMovies(val movies: List<MovieUIModel>) : HomeResult()
    class TrendingMovies(val movies: List<MovieUIModel>) : HomeResult()
}