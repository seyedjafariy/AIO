package com.worldsnas.search

import com.worldsnas.core.mvi.MviIntent
import com.worldsnas.search.model.MovieUIModel

sealed class SearchIntent : MviIntent {
    object Initial : SearchIntent()

    class Search(
        val query : String
    ) : SearchIntent()
    class NextPage(
        val query : String,
        val totalCount : Int,
        val page : Int
    ) : SearchIntent()

    class SearchResultClicked(
        val movie : MovieUIModel,
        val posterTransName : String,
        val titleTransName : String,
        val releaseTransName : String
    ): SearchIntent()
}