package com.worldsnas.moviedetail

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState

data class MovieDetailState(
    override val base: BaseState,
    val movieTitle: String
) : BaseViewState {
    companion object {
        fun start() =
            MovieDetailState(
                base = BaseState.stable(),
                movieTitle = ""
            )
    }
}