package com.worldsnas.gallery

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewConfiguration

/*
 * Created by troy379 on 14.09.16.
 */
internal class AnimationUtils private constructor() {
    init {
        throw AssertionError()
    }

    companion object {

        fun animateVisibility(view: View) {
            val isVisible = view.visibility == View.VISIBLE
            val from = if (isVisible) 1.0f else 0.0f
            val to = if (isVisible) 0.0f else 1.0f

            val animation = ObjectAnimator.ofFloat(view, "alpha", from, to)
            animation.duration = ViewConfiguration.getDoubleTapTimeout().toLong()

            if (isVisible) {
                animation.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
            } else
                view.visibility = View.VISIBLE

            animation.start()
        }
    }

}
