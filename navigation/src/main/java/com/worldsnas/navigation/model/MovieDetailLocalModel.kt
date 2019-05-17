package com.worldsnas.navigation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailLocalModel (
    val movieID : Long
) : Parcelable{
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}