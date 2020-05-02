package com.worldsnas.navigation.changehandler

import androidx.annotation.Keep
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat

class ArcFadeMoveChangeHandlerCompat : TransitionChangeHandlerCompat {
    @Keep
    constructor() : super() {
    }

    constructor(vararg transitionNames: String) : super(
        ArcFadeMoveChangeHandler(*transitionNames),
        FadeChangeHandler()
    )
}