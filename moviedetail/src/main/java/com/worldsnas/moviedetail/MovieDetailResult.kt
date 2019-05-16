package com.worldsnas.moviedetail

import com.worldsnas.base.BaseState
import com.worldsnas.mvi.MviResult

sealed class MovieDetailResult : MviResult {
    object LastStable : MovieDetailResult()
    class Error(val err: BaseState.ErrorState) : MovieDetailResult()
    class Detail(val movieTitle : String) : MovieDetailResult()
}