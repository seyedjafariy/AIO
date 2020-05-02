package com.worldsnas.navigation.changehandler

import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import com.bluelinelabs.conductor.changehandler.SharedElementTransitionChangeHandler
import com.bluelinelabs.conductor.internal.TransitionUtils
import java.util.*

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ArcFadeMoveChangeHandler : SharedElementTransitionChangeHandler {
    private val sharedElementNames = ArrayList<String>()

    @Keep
    constructor() {
    }

    constructor(vararg sharedElements: String) {
        Collections.addAll(this.sharedElementNames, *sharedElements)
    }

    override fun saveToBundle(bundle: Bundle) {
        super.saveToBundle(bundle)
        bundle.putStringArrayList(
            KEY_SHARED_ELEMENT_NAMES,
            sharedElementNames
        )
    }

    override fun restoreFromBundle(bundle: Bundle) {
        super.restoreFromBundle(bundle)
        sharedElementNames.addAll(bundle.getStringArrayList(KEY_SHARED_ELEMENT_NAMES))
    }

    override fun getExitTransition(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean
    ): Transition? {
        return Fade(Fade.OUT)
    }

    override fun getSharedElementTransition(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean
    ): Transition? {
        val transition: Transition =
            TransitionSet().addTransition(ChangeBounds())
                .addTransition(ChangeClipBounds())
                .addTransition(ChangeTransform())
        transition.pathMotion = ArcMotion()

        // The framework doesn't totally fade out the "from" shared element, so we'll hide it manually once it's safe.
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                if (from != null) {
                    for (name in sharedElementNames) {
                        val namedView =
                            TransitionUtils.findNamedView(
                                from,
                                name
                            )
                        if (namedView != null) {
                            namedView.visibility = View.INVISIBLE
                        }
                    }
                }
            }

            override fun onTransitionEnd(transition: Transition) {}
            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionResume(transition: Transition) {}
        })
        return transition
    }

    override fun getEnterTransition(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean
    ): Transition? {
        return Fade(Fade.IN)
    }

    override fun configureSharedElements(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean
    ) {
        for (name in sharedElementNames) {
            addSharedElement(name)
        }
    }

    override fun allowTransitionOverlap(isPush: Boolean): Boolean {
        return false
    }

    companion object {
        private const val KEY_SHARED_ELEMENT_NAMES =
            "ArcFadeMoveChangeHandler.sharedElementNames"
    }
}