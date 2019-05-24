package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviIntent
import com.worldsnas.navigation.model.MovieDetailLocalModel

sealed class MovieDetailIntent : MviIntent {
    class Initial(val movie: MovieDetailLocalModel) : MovieDetailIntent()
    class CoverClicked(val coverUrl : String) : MovieDetailIntent()
    object PosterClicked : MovieDetailIntent()
}