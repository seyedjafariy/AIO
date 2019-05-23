package com.worldsnas.navigation

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import com.worldsnas.navigation.model.GalleryLocalModel
import com.worldsnas.navigation.model.GalleryLocalModel.Companion.EXTRA_IMAGES
import com.worldsnas.navigation.model.MovieDetailLocalModel
import com.worldsnas.navigation.model.MovieDetailLocalModel.Companion.EXTRA_MOVIE

sealed class Screens(val name: String, val extras: Bundle? = null) {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal object Test : Screens("com.worldsnas.navigation.TestController")

    object Home : Screens("com.worldsnas.home.HomeView")

    class MovieDetail(
        movie: MovieDetailLocalModel
    ) : Screens(
        "com.worldsnas.moviedetail.view.MovieDetailView",
        bundleOf(
            EXTRA_MOVIE to movie
        )
    )

    class Gallery(
        model : GalleryLocalModel
    ) : Screens(
        "com.worldsnas.gallery.GalleryView",
        bundleOf(
            EXTRA_IMAGES to model
        )
    )
}