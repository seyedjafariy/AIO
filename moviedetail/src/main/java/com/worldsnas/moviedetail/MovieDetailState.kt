package com.worldsnas.moviedetail

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState

data class MovieDetailState(
        override val base: BaseState,
        val title: String,
        val poster: String,
        val duration: String,
        val date: String,
        val description: String,
        val covers : List<String>
) : BaseViewState {
    companion object {
        fun start() =
                MovieDetailState(
                        base = BaseState.stable(),
                        title = "",
                        poster = "",
                        duration = "",
                        date = "",
                        description = "",
                        covers = emptyList())
    }
}