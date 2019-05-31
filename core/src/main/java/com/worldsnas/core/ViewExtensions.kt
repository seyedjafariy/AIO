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

infix fun ViewGroup.inflate(id: Int): View =
    LayoutInflater.from(context).inflate(id, this, false)

fun View.getDisplaySize(): DisplaySize =
    DisplaySize(
        context.resources.displayMetrics.heightPixels,
        context.resources.displayMetrics.widthPixels
    )

infix fun View.getCenterX(container : View): Int {

    val fromLocation = IntArray(2)
    getLocationInWindow(fromLocation)

    val containerLocation = IntArray(2)
    container.getLocationInWindow(containerLocation)

    val relativeLeft = fromLocation[0] - containerLocation[0]
    val relativeTop = fromLocation[1] - containerLocation[1]

    return width / 2 + relativeLeft
}

infix fun View.getCenterY(container : View): Int {

    val fromLocation = IntArray(2)
    getLocationInWindow(fromLocation)

    val containerLocation = IntArray(2)
    container.getLocationInWindow(containerLocation)

    val relativeTop = fromLocation[1] - containerLocation[1]

    return height / 2 + relativeTop
}