package com.worldsnas.daggercore.navigator

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.worldsnas.navigation.Navigation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens

class ControllerNavigator(
    private val router: Router
) : Navigator {

    override fun goTo(screen: Screens) {
        val to = Navigation.createController(screen)
        router.pushController(RouterTransaction.with(to))
    }
}