package com.worldsnas.daggercore.navigator

import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.worldsnas.daggercore.navigator.changehandler.ArcFadeMoveChangeHandlerCompat
import com.worldsnas.daggercore.navigator.changehandler.FlipChangeHandler
import com.worldsnas.daggercore.navigator.changehandler.ScaleFadeChangeHandler
import com.worldsnas.navigation.Navigation
import com.worldsnas.navigation.NavigationAnimation
import com.worldsnas.navigation.Navigator
import com.worldsnas.navigation.Screens

class ControllerNavigator(
    private val router: Router
) : Navigator {
    override fun goTo(screen: Screens) {
        val to = Navigation.createController(screen)
        router.pushController(
            RouterTransaction.with(to)
                .pushChangeHandler(getAnimation(screen.pushAnimation))
                .popChangeHandler(getAnimation(screen.popAnimation))
        )
    }

    override fun getAnimation(anim: NavigationAnimation?): ControllerChangeHandler? {
        if (anim == null) {
            return null
        }

        return when (anim) {
            is NavigationAnimation.ArcFadeMove ->
                ArcFadeMoveChangeHandlerCompat(
                    *anim.transitionNames
                )
            is NavigationAnimation.CircularReveal ->
                TODO()
            NavigationAnimation.Flip ->
                FlipChangeHandler()
            NavigationAnimation.ScaleFade ->
                ScaleFadeChangeHandler()
        }
    }
}