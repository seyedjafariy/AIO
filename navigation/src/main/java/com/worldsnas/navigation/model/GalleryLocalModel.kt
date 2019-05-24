package com.worldsnas.navigation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GalleryLocalModel (
    val images : List<String>,
    val startPosition : Int = 0,
    val type : GalleryImageType
): Parcelable{
    companion object {
        const val EXTRA_IMAGES = "EXTRA_IMAGES"
    }
}
enum class GalleryImageType {
    COVER,
    POSTER
}