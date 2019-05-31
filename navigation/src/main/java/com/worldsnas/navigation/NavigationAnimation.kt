package com.worldsnas.navigation

sealed class NavigationAnimation {

    class ArcFadeMove(
        vararg val transitionNames: String
    ) : NavigationAnimation()

    class CircularReveal(
        val fromId: Int,
        val toId: Int
    ) : NavigationAnimation()

    object Flip : NavigationAnimation()

    object ScaleFade : NavigationAnimation()

}