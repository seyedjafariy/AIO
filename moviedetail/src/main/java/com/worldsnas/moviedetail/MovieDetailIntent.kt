package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviIntent

sealed class MovieDetailIntent : MviIntent {
    object Initial : MovieDetailIntent()
}