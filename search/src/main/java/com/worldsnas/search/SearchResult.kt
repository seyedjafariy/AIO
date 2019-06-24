package com.worldsnas.search

import com.worldsnas.mvi.MviResult

sealed class SearchResult : MviResult {
    object LastStable : SearchResult()
}