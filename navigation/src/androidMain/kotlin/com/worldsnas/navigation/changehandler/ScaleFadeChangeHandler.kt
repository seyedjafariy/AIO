package com.worldsnas.navigation.changehandler

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler

class ScaleFadeChangeHandler @Keep constructor() :
    AnimatorChangeHandler(DEFAULT_ANIMATION_DURATION, true) {
    override fun getAnimator(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean,
        toAddedToContainer: Boolean
    ): Animator {
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
            animator.play(
                ObjectAnimator.ofFloat(
                    from,
                    View.SCALE_X,
                    0.8f
                )
            )
            animator.play(
                ObjectAnimator.ofFloat(
                    from,
                    View.SCALE_Y,
                    0.8f
                )
            )
        }
        return animator
    }

    override fun resetFromView(from: View) {}
}