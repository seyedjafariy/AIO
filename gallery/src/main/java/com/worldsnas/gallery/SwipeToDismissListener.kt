/*
 * Copyright (C) 2016 stfalcon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.worldsnas.gallery

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
internal class SwipeToDismissListener(
    private val swipeView: View, private val dismissListener: OnDismissListener?,
    private val moveListener: OnViewMoveListener?
) : View.OnTouchListener {
    private var translationLimit: Int = 0

    private var tracking = false
    private var startY: Float = 0.toFloat()

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        translationLimit = v.height / 4
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val hitRect = Rect()
                swipeView.getHitRect(hitRect)
                if (hitRect.contains(event.x.toInt(), event.y.toInt())) {
                    tracking = true
                }
                startY = event.y
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (tracking) {
                    tracking = false
                    animateSwipeView(v.height)
                }
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (tracking) {
                    val translationY = event.y - startY
                    swipeView.translationY = translationY
                    callMoveListener(translationY, translationLimit)
                }
                return true
            }
        }
        return false
    }

    private fun animateSwipeView(parentHeight: Int) {
        val currentPosition = swipeView.translationY
        var animateTo = 0.0f

        if (currentPosition < -translationLimit) {
            animateTo = (-parentHeight).toFloat()
        } else if (currentPosition > translationLimit) {
            animateTo = parentHeight.toFloat()
        }

        val isDismissed = animateTo != 0.0f
        val animator = ObjectAnimator.ofFloat(
            swipeView, PROPERTY_TRANSLATION_X, currentPosition, animateTo
        )

        animator.duration = 200
        animator.interpolator = AccelerateInterpolator()
        animator.addListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    if (isDismissed) callDismissListener()
                }
            })
        animator.addUpdateListener { animation -> callMoveListener(animation.animatedValue as Float, translationLimit) }
        animator.start()
    }

    private fun callDismissListener() {
        dismissListener?.onDismiss()
    }

    private fun callMoveListener(translationY: Float, translationLimit: Int) {
        moveListener?.onViewMove(translationY, translationLimit)
    }

    internal interface OnViewMoveListener {
        fun onViewMove(translationY: Float, translationLimit: Int)
    }

    companion object {

        private val PROPERTY_TRANSLATION_X = "translationY"
    }
}