package com.worldsnas.moviedetail

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.core.mvi.MviResult
import com.worldsnas.moviedetail.model.GenreUIModel
import com.worldsnas.moviedetail.model.MovieUIModel

sealed class MovieDetailResult : MviResult {
    object LastStable : MovieDetailResult()
    class Error(val err: BaseState.ErrorState) : MovieDetailResult()
    class Detail(
        val title: String,
        val poster: String,
        val duration: String,
        val date: String,
        val description: String,
        val covers: List<String>,
        val genres: List<GenreUIModel>,
        val recommendations: List<MovieUIModel>,
        val similars: List<MovieUIModel>
    ) : MovieDetailResult()
}