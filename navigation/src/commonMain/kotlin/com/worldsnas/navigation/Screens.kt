package com.worldsnas.navigation

import com.worldsnas.navigation.model.GalleryLocalModel
import com.worldsnas.navigation.model.GalleryLocalModel.Sinker.EXTRA_IMAGES
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.navigation.model.MovieDetailLocalModel.Sinker.EXTRA_MOVIE
import com.worldsnas.navigation.model.SearchLocalModel
import com.worldsnas.navigation.model.SearchLocalModel.Sinker.EXTRA_SEARCH

sealed class Screens(
    val name: String,
    val extras: Pair<String, ByteArray>? = null,
    val pushAnimation: NavigationAnimation? = null,
    val popAnimation: NavigationAnimation? = null
) {

    interface Dynamic {
        val module: String
    }

    internal object Test : Screens("com.worldsnas.navigation.TestController")

    object Home : Screens("com.worldsnas.home.view.HomeView")

    class MovieDetail(
        movie: MovieDetailLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.moviedetail.view.MovieDetailView",
        EXTRA_MOVIE to MovieDetailLocalModel.toByteArray(movie),
        pushAnimation,
        popAnimation
    )

    class Gallery(
        model: GalleryLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.gallery.GalleryView",
        EXTRA_IMAGES to GalleryLocalModel.toByteArray(model),
        pushAnimation,
        popAnimation
    )

    class Search(
        model: SearchLocalModel,
        pushAnimation: NavigationAnimation? = null,
        popAnimation: NavigationAnimation? = null
    ) : Screens(
        "com.worldsnas.search.view.SearchView",
        EXTRA_SEARCH to SearchLocalModel.toByteArray(model),
        pushAnimation,
        popAnimation
    ), Dynamic {
        override val module: String
            get() = "search"
    }
}