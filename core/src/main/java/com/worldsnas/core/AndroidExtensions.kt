package com.worldsnas.core

import android.content.Context

fun Context.getScreenWidth(): Int =
    resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int =
    resources.displayMetrics.widthPixels