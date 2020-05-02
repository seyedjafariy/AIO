package com.worldsnas.navigation

sealed class NavigationAnimation {

    class ArcFadeMove(
        vararg val transitionNames: String
    ) : NavigationAnimation()

    class CircularReveal(
        val fromCX: Int,
        val fromCY: Int,
        val duration : Long = -1
    ) : NavigationAnimation()

    object Flip : NavigationAnimation()

    object ScaleFade : NavigationAnimation()

}