package com.daimajia.slider.library

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.Carousel

class HorizontalCarousel @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : Carousel(context, attr, defStyle) {

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}