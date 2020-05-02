package com.worldsnas.navigation.changehandler

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Property
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.annotation.Keep
import com.bluelinelabs.conductor.changehandler.AnimatorChangeHandler

class FlipChangeHandler @JvmOverloads constructor(
    private val flipDirection: FlipDirection,
    private val animationDurationMillis: Long = DEFAULT_ANIMATION_DURATION
) : AnimatorChangeHandler() {
    enum class FlipDirection(
        val inStartRotation: Int,
        val outEndRotation: Int,
        val property: Property<View, Float>
    ) {
        LEFT(-180, 180, View.ROTATION_Y), RIGHT(
            180,
            -180,
            View.ROTATION_Y
        ),
        UP(-180, 180, View.ROTATION_X), DOWN(180, -180, View.ROTATION_X);

    }

    @Keep
    constructor() : this(FlipDirection.RIGHT) {
    }

    constructor(animationDuration: Long) : this(FlipDirection.RIGHT, animationDuration) {}

    override fun getAnimator(
        container: ViewGroup,
        from: View?,
        to: View?,
        isPush: Boolean,
        toAddedToContainer: Boolean
    ): Animator {
        val animatorSet = AnimatorSet()
        if (to != null) {
            to.alpha = 0f
            val rotation =
                ObjectAnimator.ofFloat(
                    to,
                    flipDirection.property,
                    flipDirection.inStartRotation.toFloat(),
                    0f
                ).setDuration(animationDurationMillis)
            rotation.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.play(rotation)
            val alpha: Animator =
                ObjectAnimator.ofFloat(
                    to,
                    View.ALPHA,
                    1f
                ).setDuration(animationDurationMillis / 2)
            alpha.startDelay = animationDurationMillis / 3
            animatorSet.play(alpha)
        }
        if (from != null) {
            val rotation =
                ObjectAnimator.ofFloat(
                    from,
                    flipDirection.property,
                    0f,
                    flipDirection.outEndRotation.toFloat()
                ).setDuration(animationDurationMillis)
            rotation.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.play(rotation)
            val alpha: Animator =
                ObjectAnimator.ofFloat(
                    from,
                    View.ALPHA,
                    0f
                ).setDuration(animationDurationMillis / 2)
            alpha.startDelay = animationDurationMillis / 3
            animatorSet.play(alpha)
        }
        return animatorSet
    }

    override fun resetFromView(from: View) {
        from.alpha = 1f
        when (flipDirection) {
            FlipDirection.LEFT, FlipDirection.RIGHT -> from.rotationY = 0f
            FlipDirection.UP, FlipDirection.DOWN -> from.rotationX = 0f
        }
    }

    companion object {
        private const val DEFAULT_ANIMATION_DURATION: Long = 300
    }

}