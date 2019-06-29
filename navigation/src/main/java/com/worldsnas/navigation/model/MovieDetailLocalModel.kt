package com.worldsnas.navigation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailLocalModel(
        val movieID: Long,
        val poster: String,
        val cover: String,
        val title: String,
        val description: String,
        val releasedDate: String,
        val posterTransName : String = "",
        val releaseTransName : String = "",
        val titleTransName : String = "",
        val coverTransName : String = ""
) : Parcelable {
    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}