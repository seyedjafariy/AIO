package com.worldsnas.search

import com.worldsnas.mvi.MviIntent

sealed class SearchIntent : MviIntent {
    object Initial : SearchIntent()
}