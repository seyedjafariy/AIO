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

package com.worldsnas.gallery.drawee

import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.view.DraweeView
import me.relex.photodraweeview.Attacher
import me.relex.photodraweeview.OnScaleChangeListener

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
internal class NonInterceptableAttacher(draweeView: DraweeView<GenericDraweeHierarchy>) : Attacher(draweeView) {

    private var scaleChangeListener: OnScaleChangeListener? = null

    override fun onDrag(dx: Float, dy: Float) {
        val draweeView = draweeView
        if (draweeView != null) {
            drawMatrix.postTranslate(dx, dy)
            checkMatrixAndInvalidate()

            val parent = draweeView.parent ?: return

            if (scale == 1.0f) {
                parent.requestDisallowInterceptTouchEvent(false)
            } else {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        }
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    override fun setOnScaleChangeListener(listener: OnScaleChangeListener) {
        this.scaleChangeListener = listener
    }

    override fun onScale(scaleFactor: Float, focusX: Float, focusY: Float) {
        super.onScale(scaleFactor, focusX, focusY)
        if (scaleChangeListener != null) {
            scaleChangeListener!!.onScaleChange(scaleFactor, focusX, focusY)
        }
    }
}