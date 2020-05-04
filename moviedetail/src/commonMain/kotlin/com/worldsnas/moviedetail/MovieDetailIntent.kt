package com.worldsnas.moviedetail

import com.worldsnas.core.mvi.MviIntent
import com.worldsnas.moviedetail.model.MovieUIModel
import com.worldsnas.navigation.model.MovieDetailLocalModel

sealed class MovieDetailIntent : MviIntent {
    class Initial(val movie: MovieDetailLocalModel) : MovieDetailIntent()
    class CoverClicked(
        val coverUrl : String,
        val cx : Int,
        val cy : Int
    ) : MovieDetailIntent()
    class PosterClicked(
        val cx : Int,
        val cy : Int
    ) : MovieDetailIntent()

    class RecommendationClicked(
        val movie : MovieUIModel,
        val posterTransName : String = "",
        val titleTransName : String = "",
        val releaseTransName : String = ""
    ) : MovieDetailIntent()
}