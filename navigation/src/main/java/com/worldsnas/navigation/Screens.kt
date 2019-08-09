package com.worldsnas.navigation

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import com.worldsnas.navigation.model.GalleryLocalModel
import com.worldsnas.navigation.model.GalleryLocalModel.Companion.EXTRA_IMAGES
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.navigation.model.MovieDetailLocalModel.Companion.EXTRA_MOVIE
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.navigation.model.SearchLocalModel.Companion.EXTRA_SEARCH

sealed class Screens(
    val name: String,
    val extras: Bundle? = null,
    val pushAnimation: NavigationAnimation? = null,
    val popAnimation: NavigationAnimation? = null
) {

    interface Split{
        val module : String
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal object Test : Screens("com.worldsnas.navigation.TestController")

    object Home : Screens("com.worldsnas.home.view.HomeView")

    class MovieDetail(
        movie: MovieDetailLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.moviedetail.view.MovieDetailView",
        bundleOf(
            EXTRA_MOVIE to movie
        ),
        pushAnimation,
        popAnimation
    )

    class Gallery(
        model: GalleryLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.gallery.GalleryView",
        bundleOf(
            EXTRA_IMAGES to model
        ),
        pushAnimation,
        popAnimation
    )

    class Search(
        model : SearchLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.search.view.SearchView",
        bundleOf(
            EXTRA_SEARCH to model
        ),
        pushAnimation,
        popAnimation
    ), Split {
        override val module: String
            get() = "search"
    }
}