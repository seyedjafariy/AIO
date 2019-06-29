package com.worldsnas.navigation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchLocalModel(
    val backTransitionName : String = "",
    val textTransitionName : String = ""
) : Parcelable{
    companion object{
        const val EXTRA_SEARCH = "extra_search"
    }
}