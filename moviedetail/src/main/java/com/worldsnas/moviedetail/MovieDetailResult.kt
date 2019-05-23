package com.worldsnas.moviedetail

import com.worldsnas.base.BaseState
import com.worldsnas.mvi.MviResult

sealed class MovieDetailResult : MviResult {
    object LastStable : MovieDetailResult()
    class Error(val err: BaseState.ErrorState) : MovieDetailResult()
    class Detail(
            val title: String,
            val poster: String,
            val duration: String,
            val date: String,
            val description: String,
            val covers : List<String>
    ) : MovieDetailResult()
}