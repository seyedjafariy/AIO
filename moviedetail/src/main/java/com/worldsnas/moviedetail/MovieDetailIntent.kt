package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviIntent
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
}