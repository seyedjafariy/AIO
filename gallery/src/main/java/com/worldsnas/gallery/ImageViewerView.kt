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
import android.util.AttributeSet
import android.view.*
import android.widget.RelativeLayout
import androidx.core.view.GestureDetectorCompat
import androidx.viewpager.widget.ViewPager
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.gallery.adapter.ImageViewerAdapter

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
internal class ImageViewerView : RelativeLayout, OnDismissListener, SwipeToDismissListener.OnViewMoveListener {

    private var backgroundView: View? = null
    private var pager: MultiTouchViewPager? = null
    private var adapter: ImageViewerAdapter? = null
    private var directionDetector: SwipeDirectionDetector? = null
    private var scaleDetector: ScaleGestureDetector? = null
    private var pageChangeListener: ViewPager.OnPageChangeListener? = null
    private var gestureDetector: GestureDetectorCompat? = null

    private lateinit var dismissContainer: ViewGroup
    private var swipeDismissListener: SwipeToDismissListener? = null
    private var overlayView: View? = null

    private var direction: SwipeDirectionDetector.Direction? = null

    private var customImageRequestBuilder: ImageRequestBuilder? = null
    private var customDraweeHierarchyBuilder: GenericDraweeHierarchyBuilder? = null

    private var wasScaled: Boolean = false
    private var onDismissListener: OnDismissListener? = null
    private var isOverlayWasClicked: Boolean = false

    private var isZoomingAllowed = true
    private var isSwipeToDismissAllowed = true

    val isScaled: Boolean
        get() = adapter!!.isScaled(pager!!.currentItem)

    val url: String
        get() = adapter!!.getUrl(pager!!.currentItem)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun setUrls(dataSet: List<String>, startPosition: Int) {
        adapter = ImageViewerAdapter(
            context, dataSet, customImageRequestBuilder, customDraweeHierarchyBuilder, isZoomingAllowed
        )
        pager!!.adapter = adapter
        setStartPosition(startPosition)
    }

    fun setCustomImageRequestBuilder(customImageRequestBuilder: ImageRequestBuilder) {
        this.customImageRequestBuilder = customImageRequestBuilder
    }

    fun setCustomDraweeHierarchyBuilder(customDraweeHierarchyBuilder: GenericDraweeHierarchyBuilder) {
        this.customDraweeHierarchyBuilder = customDraweeHierarchyBuilder
    }

    override fun setBackgroundColor(color: Int) {
        findViewById<View>(R.id.backgroundView)
            .setBackgroundColor(color)
    }

    fun setOverlayView(view: View) {
        this.overlayView = view
        if (overlayView != null) {
            dismissContainer.addView(view)
        }
    }

    fun allowZooming(allowZooming: Boolean) {
        this.isZoomingAllowed = allowZooming
    }

    fun allowSwipeToDismiss(allowSwipeToDismiss: Boolean) {
        this.isSwipeToDismissAllowed = allowSwipeToDismiss
    }

    fun setImageMargin(marginPixels: Int) {
        pager!!.pageMargin = marginPixels
    }

    fun setContainerPadding(paddingPixels: IntArray) {
        pager!!.setPadding(
            paddingPixels[0],
            paddingPixels[1],
            paddingPixels[2],
            paddingPixels[3]
        )
    }

    private fun init() {
        View.inflate(context, R.layout.image_viewer, this)

        backgroundView = findViewById(R.id.backgroundView)
        pager = findViewById(R.id.pager)

        dismissContainer = findViewById(R.id.container)
        swipeDismissListener = SwipeToDismissListener(findViewById(R.id.dismissView), this, this)
        dismissContainer.setOnTouchListener(swipeDismissListener)

        directionDetector = object : SwipeDirectionDetector(context) {
            override fun onDirectionDetected(direction: Direction) {
                this@ImageViewerView.direction = direction
            }
        }

        scaleDetector = ScaleGestureDetector(
            context,
            ScaleGestureDetector.SimpleOnScaleGestureListener()
        )

        gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (pager!!.isScrolled) {
                    onClick(e, isOverlayWasClicked)
                }
                return false
            }
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        onUpDownEvent(event)

        if (direction == null) {
            if (scaleDetector!!.isInProgress || event.pointerCount > 1) {
                wasScaled = true
                return pager!!.dispatchTouchEvent(event)
            }
        }

        if (!adapter!!.isScaled(pager!!.currentItem)) {
            directionDetector!!.onTouchEvent(event)
            if (direction != null) {
                when (direction) {
                    SwipeDirectionDetector.Direction.UP, SwipeDirectionDetector.Direction.DOWN ->
                        return if (isSwipeToDismissAllowed && !wasScaled && pager!!.isScrolled) {
                            swipeDismissListener!!.onTouch(dismissContainer, event)
                        } else {
                            true
                        }
                    SwipeDirectionDetector.Direction.LEFT, SwipeDirectionDetector.Direction.RIGHT ->
                        return pager!!.dispatchTouchEvent(
                            event
                        )
                    SwipeDirectionDetector.Direction.NOT_DETECTED ->
                        return false
                }
            }
            return true
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onDismiss() {
        if (onDismissListener != null) {
            onDismissListener!!.onDismiss()
        }
    }

    override fun onViewMove(translationY: Float, translationLimit: Int) {
        val alpha = 1.0f - 1.0f / translationLimit.toFloat() / 4f * Math.abs(translationY)
        backgroundView!!.alpha = alpha
        if (overlayView != null) overlayView!!.alpha = alpha
    }

    fun setOnDismissListener(onDismissListener: OnDismissListener) {
        this.onDismissListener = onDismissListener
    }

    fun resetScale() {
        adapter!!.resetScale(pager!!.currentItem)
    }

    fun setPageChangeListener(pageChangeListener: ViewPager.OnPageChangeListener) {
        pager!!.removeOnPageChangeListener(this.pageChangeListener!!)
        this.pageChangeListener = pageChangeListener
        pager!!.addOnPageChangeListener(pageChangeListener)
        pageChangeListener.onPageSelected(pager!!.currentItem)
    }

    private fun setStartPosition(position: Int) {
        pager!!.currentItem = position
    }

    private fun onUpDownEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_UP) {
            onActionUp(event)
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            onActionDown(event)
        }

        scaleDetector!!.onTouchEvent(event)
        gestureDetector!!.onTouchEvent(event)
    }

    private fun onActionDown(event: MotionEvent) {
        direction = null
        wasScaled = false
        pager!!.dispatchTouchEvent(event)
        swipeDismissListener!!.onTouch(dismissContainer, event)
        isOverlayWasClicked = dispatchOverlayTouch(event)
    }

    private fun onActionUp(event: MotionEvent) {
        swipeDismissListener!!.onTouch(dismissContainer, event)
        pager!!.dispatchTouchEvent(event)
        isOverlayWasClicked = dispatchOverlayTouch(event)
    }

    private fun onClick(event: MotionEvent, isOverlayWasClicked: Boolean) {
        if (overlayView != null && !isOverlayWasClicked) {
            AnimationUtils.animateVisibility(overlayView!!)
            super.dispatchTouchEvent(event)
        }
    }

    private fun dispatchOverlayTouch(event: MotionEvent): Boolean {
        return (overlayView != null
                && overlayView!!.visibility == View.VISIBLE
                && overlayView!!.dispatchTouchEvent(event))
    }

}
