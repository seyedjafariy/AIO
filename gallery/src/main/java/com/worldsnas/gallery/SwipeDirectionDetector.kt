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

import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
internal abstract class SwipeDirectionDetector(context: Context) {

    private val touchSlop: Int
    private var startX: Float = 0.toFloat()
    private var startY: Float = 0.toFloat()
    private var isDetected: Boolean = false

    abstract fun onDirectionDetected(direction: Direction)

    init {
        this.touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                if (!isDetected) {
                    onDirectionDetected(Direction.NOT_DETECTED)
                }
                startY = 0.0f
                startX = startY
                isDetected = false
            }
            MotionEvent.ACTION_MOVE -> if (!isDetected && getDistance(event) > touchSlop) {
                isDetected = true
                val x = event.x
                val y = event.y

                val direction = getDirection(startX, startY, x, y)
                onDirectionDetected(direction)
            }
        }
        return false
    }

    /**
     * Given two points in the plane p1=(x1, x2) and p2=(y1, y1), this method
     * returns the direction that an arrow pointing from p1 to p2 would have.
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the direction
     */
    fun getDirection(x1: Float, y1: Float, x2: Float, y2: Float): Direction {
        val angle = getAngle(x1, y1, x2, y2)
        return Direction[angle]
    }

    /**
     * Finds the angle between two points in the plane (x1,y1) and (x2, y2)
     * The angle is measured with 0/360 being the X-axis to the right, angles
     * increase counter clockwise.
     *
     * @param x1 the x position of the first point
     * @param y1 the y position of the first point
     * @param x2 the x position of the second point
     * @param y2 the y position of the second point
     * @return the angle between two points
     */
    fun getAngle(x1: Float, y1: Float, x2: Float, y2: Float): Double {
        val rad = Math.atan2((y1 - y2).toDouble(), (x2 - x1).toDouble()) + Math.PI
        return (rad * 180 / Math.PI + 180) % 360
    }

    private fun getDistance(ev: MotionEvent): Float {
        var distanceSum = 0f

        val dx = ev.getX(0) - startX
        val dy = ev.getY(0) - startY
        distanceSum += Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()

        return distanceSum
    }

    enum class Direction {
        NOT_DETECTED,
        UP,
        DOWN,
        LEFT,
        RIGHT;


        companion object {

            operator fun get(angle: Double): Direction {
                return if (inRange(angle, 45f, 135f)) {
                    Direction.UP
                } else if (inRange(angle, 0f, 45f) || inRange(angle, 315f, 360f)) {
                    Direction.RIGHT
                } else if (inRange(angle, 225f, 315f)) {
                    Direction.DOWN
                } else {
                    Direction.LEFT
                }
            }

            private fun inRange(angle: Double, init: Float, end: Float): Boolean {
                return angle >= init && angle < end
            }
        }
    }
}
