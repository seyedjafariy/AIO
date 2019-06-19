package com.worldsnas.moviedetail

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel

data class MovieDetailState(
        override val base: BaseState,
        val title: String,
        val poster: String,
        val duration: String,
        val date: String,
        val description: String,
        val covers : List<String>,
        val genres : List<GenreUIModel>,
        val recommendations : List<MovieUIModel>
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
                        covers = emptyList(),
                        genres = emptyList(),
                        recommendations = emptyList()
                        )
    }
}