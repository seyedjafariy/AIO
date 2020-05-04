package com.worldsnas.moviedetail

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.BaseViewState
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
        val recommendations : List<MovieUIModel>,
        val showRecommendation : Boolean,
        val similars : List<MovieUIModel>,
        val showSimilar : Boolean
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
                        recommendations = emptyList(),
                        showRecommendation = false,
                        similars = emptyList(),
                        showSimilar = false)
    }
}