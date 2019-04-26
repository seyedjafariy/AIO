package com.worldsnas.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@Suppress("LiftReturnOrAssignment")
infix fun View.visible(visible: Boolean) =
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }

infix fun ViewGroup.inflate(id : Int) : View =
    LayoutInflater.from(context).inflate(id, this, false)

fun View.getDisplaySize(): DisplaySize =
        DisplaySize(
                context.resources.displayMetrics.heightPixels,
                context.resources.displayMetrics.widthPixels
        )