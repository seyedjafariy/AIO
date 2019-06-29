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
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/*
 * Created by Alexander Krol (troy379) on 29.08.16.
 */
internal class MultiTouchViewPager : ViewPager {

    private var isDisallowIntercept: Boolean = false
    var isScrolled = true
        private set

    constructor(context: Context) : super(context) {
        setScrollStateListener()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setScrollStateListener()
    }

    override fun requestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        isDisallowIntercept = disallowIntercept
        super.requestDisallowInterceptTouchEvent(disallowIntercept)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.pointerCount > 1 && isDisallowIntercept) {
            requestDisallowInterceptTouchEvent(false)
            val handled = super.dispatchTouchEvent(ev)
            requestDisallowInterceptTouchEvent(true)
            return handled
        } else {
            return super.dispatchTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (ev.pointerCount > 1) {
            false
        } else {
            try {
                super.onInterceptTouchEvent(ev)
            } catch (ex: IllegalArgumentException) {
                false
            }

        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return try {
            super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            false
        }

    }

    private fun setScrollStateListener() {
        addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageScrollStateChanged(state: Int) {
                isScrolled = state == ViewPager.SCROLL_STATE_IDLE
            }
        })
    }
}