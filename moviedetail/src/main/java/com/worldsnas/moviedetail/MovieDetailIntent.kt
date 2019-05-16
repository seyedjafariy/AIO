package com.worldsnas.moviedetail

import com.worldsnas.mvi.MviIntent

sealed class MovieDetailIntent : MviIntent {
    class Initial(val movieID: Long) : MovieDetailIntent()
}