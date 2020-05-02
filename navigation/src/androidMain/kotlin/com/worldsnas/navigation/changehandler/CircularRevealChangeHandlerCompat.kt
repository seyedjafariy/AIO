package com.worldsnas.navigation.changehandler

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep

class CircularRevealChangeHandlerCompat : CircularRevealChangeHandler {
    @Keep
    constructor() {
    }

    constructor(fromView: View, containerView: View) : super(
        fromView,
        containerView
    ) {
    }

    constructor(cx: Int, cy: Int) : super(cx, cy) {}
    constructor(cx: Int, cy: Int, duration: Long) : super(cx, cy, duration) {}

    override fun getAnimator(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean,
        toAddedToContainer: Boolean
    ): Animator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.getAnimator(container, from, to, isPush, toAddedToContainer)
        } else {
            val animator = AnimatorSet()
            if (to != null) {
                val start: Float = if (toAddedToContainer) 0F else to.alpha
                animator.play(
                    ObjectAnimator.ofFloat(
                        to,
                        View.ALPHA,
                        start,
                        1f
                    )
                )
            }
            if (from != null) {
                animator.play(
                    ObjectAnimator.ofFloat(
                        from,
                        View.ALPHA,
                        0f
                    )
                )
            }
            animator
        }
    }
}