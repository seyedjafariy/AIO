package com.worldsnas.navigation

import com.bluelinelabs.conductor.ControllerChangeHandler

interface Navigator {

    fun goTo(screen: Screens)

    fun getAnimation(anim: NavigationAnimation?): ControllerChangeHandler?
}