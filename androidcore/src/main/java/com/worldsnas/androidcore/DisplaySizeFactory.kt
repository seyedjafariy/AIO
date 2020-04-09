package com.worldsnas.androidcore

import android.content.Context
import dagger.Reusable
import javax.inject.Inject

@Reusable
class DisplaySizeFactory @Inject constructor(
) {
    fun create(context: Context): DisplaySize =
        DisplaySize(
            context.resources.displayMetrics.heightPixels,
            context.resources.displayMetrics.widthPixels
        )
}