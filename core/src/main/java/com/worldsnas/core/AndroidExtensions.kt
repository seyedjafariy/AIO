package com.worldsnas.core

import android.content.Context
import android.util.DisplayMetrics


fun Context.getScreenWidth(): Int =
        resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int =
        resources.displayMetrics.widthPixels


infix fun Float.pixel(context: Context): Float =
        this *
                (context.resources.displayMetrics.densityDpi.toFloat() /
                        DisplayMetrics.DENSITY_DEFAULT)

infix fun Float.dp(context: Context): Float =
        this /
                (context.resources.displayMetrics.densityDpi.toFloat() /
                        DisplayMetrics.DENSITY_DEFAULT)
