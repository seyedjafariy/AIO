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

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.view.SimpleDraweeView

import me.relex.photodraweeview.IAttacher
import me.relex.photodraweeview.OnPhotoTapListener
import me.relex.photodraweeview.OnScaleChangeListener
import me.relex.photodraweeview.OnViewTapListener

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
class ZoomableDraweeView : SimpleDraweeView, IAttacher {

    private var attacher: NonInterceptableAttacher? = null

    constructor(context: Context, hierarchy: GenericDraweeHierarchy) : super(context, hierarchy) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    protected fun init() {
        if (attacher == null || attacher!!.draweeView == null) {
            attacher = NonInterceptableAttacher(this)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        val saveCount = canvas.save()
        canvas.concat(attacher!!.drawMatrix)
        super.onDraw(canvas)
        canvas.restoreToCount(saveCount)
    }

    override fun onAttachedToWindow() {
        init()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        attacher!!.onDetachedFromWindow()
        super.onDetachedFromWindow()
    }

    override fun getMinimumScale(): Float {
        return attacher!!.minimumScale
    }

    override fun getMediumScale(): Float {
        return attacher!!.mediumScale
    }

    override fun getMaximumScale(): Float {
        return attacher!!.maximumScale
    }

    override fun setMinimumScale(minimumScale: Float) {
        attacher!!.minimumScale = minimumScale
    }

    override fun setMediumScale(mediumScale: Float) {
        attacher!!.mediumScale = mediumScale
    }

    override fun setMaximumScale(maximumScale: Float) {
        attacher!!.maximumScale = maximumScale
    }

    override fun getScale(): Float {
        return attacher!!.scale
    }

    override fun setScale(scale: Float) {
        attacher!!.scale = scale
    }

    override fun setScale(scale: Float, animate: Boolean) {
        setScale(scale, (right / 2).toFloat(), (bottom / 2).toFloat(), animate)
    }

    override fun setScale(scale: Float, focalX: Float, focalY: Float, animate: Boolean) {
        attacher!!.setScale(scale, focalX, focalY, animate)
    }

    override fun setOrientation(orientation: Int) {
        attacher!!.setOrientation(orientation)
    }

    override fun setZoomTransitionDuration(duration: Long) {
        attacher!!.setZoomTransitionDuration(duration)
    }

    override fun setAllowParentInterceptOnEdge(allow: Boolean) {
        attacher!!.setAllowParentInterceptOnEdge(allow)
    }

    override fun setOnDoubleTapListener(listener: GestureDetector.OnDoubleTapListener) {
        attacher!!.setOnDoubleTapListener(listener)
    }

    override fun setOnScaleChangeListener(listener: OnScaleChangeListener) {
        attacher!!.setOnScaleChangeListener(listener)
    }

    override fun setOnLongClickListener(listener: View.OnLongClickListener?) {
        attacher!!.setOnLongClickListener(listener)
    }

    override fun setOnPhotoTapListener(listener: OnPhotoTapListener) {
        attacher!!.onPhotoTapListener = listener
    }

    override fun setOnViewTapListener(listener: OnViewTapListener) {
        attacher!!.onViewTapListener = listener
    }

    override fun getOnPhotoTapListener(): OnPhotoTapListener {
        return attacher!!.onPhotoTapListener
    }

    override fun getOnViewTapListener(): OnViewTapListener {
        return attacher!!.onViewTapListener
    }

    override fun update(imageInfoWidth: Int, imageInfoHeight: Int) {
        attacher!!.update(imageInfoWidth, imageInfoHeight)
    }
}
