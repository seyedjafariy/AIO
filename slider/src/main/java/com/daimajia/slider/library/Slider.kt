package com.daimajia.slider.library

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelView
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

@ModelView(saveViewState = true, autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class Slider @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : Carousel(context, attr, defStyle) {

    private var timer: Timer? = null

    private val timerDelay: Long = 3000

    private val linearLayoutManager: LinearLayoutManager
        get() = layoutManager as LinearLayoutManager

    private val size = AtomicInteger(0)

    private var sliderTask : TimerTask? = null

    private val scrollListener = ScrollListener()

    class ScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {

            } else if (RecyclerView.SCROLL_STATE_DRAGGING == newState) {

            }
        }
    }

    override fun createLayoutManager(): LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        timer = Timer()
        size.set(adapter?.itemCount ?: 0)
        scedule()
        addOnScrollListener(scrollListener)
    }

    override fun setModels(models: List<EpoxyModel<*>>) {
        super.setModels(models)
        size.set(models.size)
        scedule()
    }

    override fun onDetachedFromWindow() {
        size.set(0)
        stopSlider()
        timer?.cancel()
        removeOnScrollListener(scrollListener)
        super.onDetachedFromWindow()
    }


    private fun scedule() {
        stopSlider()
        sliderTask = object : TimerTask() {
            override fun run() {
                scrollToNextSlide()
            }
        }
        timer?.schedule(sliderTask, timerDelay)
    }

    private fun stopSlider() {
        sliderTask?.cancel()
        sliderTask = null
    }

    private fun scrollToNextSlide() {
        val position = linearLayoutManager.findFirstVisibleItemPosition()
        if (size.get() != 0) {
            if (position + 1 < size.get()) {
                smoothScrollToPosition(position + 1)
            } else {
                scrollToPosition(0)
            }
        }
    }
}