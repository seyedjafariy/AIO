package com.worldsnas.navigation

import com.bluelinelabs.conductor.Controller

object Navigation {

    const val HOME = "com.worldsnas.home.HomeView"

    @JvmStatic
    fun createController(name: String): Controller =
        Class.forName(name).constructors.first().newInstance() as Controller
}