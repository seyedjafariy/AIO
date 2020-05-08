package com.daimajia.slider.library

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.*
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

typealias ModelCopier = (yours: EpoxyModel<*>) -> EpoxyModel<*>

@ModelView(
    saveViewState = true,
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT
)
class Slider @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attr, defStyle) {
    
    private val carousel : HorizontalCarousel
    private val indicator : IndefinitePagerIndicator
    
    private var controller: AsyncSimpleController = AsyncSimpleController()

    private var timer: Timer? = null

    private var timerDelay: Long = 5000

    private val linearLayoutManager: LinearLayoutManager
        get() = carousel
            .layoutManager as LinearLayoutManager

    private val size = AtomicInteger(0)

    private var sliderTask: TimerTask? = null

    private var infinite: Boolean = false
    private var showIndicator: Boolean = true
    private val infiniteSize = AtomicInteger(0)
    private var infiniteModels: List<EpoxyModel<*>> = emptyList()

    private var models: List<EpoxyModel<*>> = emptyList()

    private var copier: ModelCopier? = null

    init {
        val root = LayoutInflater
            .from(context)
            .inflate(R.layout.slider_slider_view, this, false)
        
        addView(root)
        carousel = root.findViewById(R.id.slider_slide_carousel)
        indicator = root.findViewById(R.id.slider_slide_indicator)
        
        carousel
            .setPadding(Carousel.Padding.dp(8, 8))
        carousel.setController(controller)
        carousel.numViewsToShowOnScreen = 1.1F
        carousel.setInitialPrefetchItemCount(3)

        controller.addModelBuildListener(object : OnModelBuildFinishedListener {
            override fun onModelBuildFinished(result: DiffResult) {
                if (infinite) {
                    linearLayoutManager.scrollToPosition(3)
                }
                controller.removeModelBuildListener(this)
            }
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        size.set(carousel.adapter?.itemCount ?: 0)
        schedule()
    }

    @JvmOverloads
    @ModelProp(options = [ModelProp.Option.DoNotHash])
    fun controller(controller: AsyncSimpleController? = null) {
        if (controller != null) {
            this.controller = controller
            carousel.setController(controller)
        }
    }

    @ModelProp
    @JvmOverloads
    fun cycleDelay(delay: Long = 5000) {
        timerDelay = delay
    }

    @OnViewRecycled
    fun viewRecycled() {
        stopSlider()
    }

    @ModelProp
    fun setModels(models: List<EpoxyModel<*>>) {
        this.models = models
        size.set(models.size)
    }

    @ModelProp
    @JvmOverloads
    fun setInfinite(infinite: Boolean = false) {
        this.infinite = infinite
    }

    @ModelProp
    @JvmOverloads
    fun setIndicatorVisible(visible: Boolean = true) {
        this.showIndicator = visible
    }

    @CallbackProp
    fun setCopier(copier: ModelCopier?) {
        this.copier = copier
    }

    @AfterPropsSet
    fun setModelsToController() {
        if (infinite && models.size >= 3) {
            val copy = copier ?: throw IllegalStateException(
                "for infinite scrolls copier must be set"
            )

            infiniteModels = models.toMutableList().apply {
                addAll(models.subList(0, 3).map {
                    val newModel = copy(it)
                    newModel.id("copied version= ${newModel.id()}")
                    newModel
                })
                addAll(0, models.subList(models.size - 3, models.size)
                    .map {
                        val newModel = copy(it)
                        newModel.id("copied version= ${newModel.id()}")
                        newModel
                    })
            }
            infiniteSize.set(infiniteModels.size)
            controller.setModels(infiniteModels)
            carousel.addOnScrollListener(
                InfiniteScroller(linearLayoutManager, infiniteModels.size)
            )
        } else {
            controller.setModels(models)
        }

        if (showIndicator) {
            indicator.visibility = View.VISIBLE
            indicator.attachToRecyclerView(carousel)
        }else{
            indicator.visibility = View.GONE
        }

        schedule()
    }

    override fun onDetachedFromWindow() {
        size.set(0)
        stopSlider()
        timer?.cancel()
        super.onDetachedFromWindow()
    }


    private fun schedule() {
        stopSlider()

        if (size.get() == 0) {
            return
        }

        timer = Timer()
        sliderTask = object : TimerTask() {
            override fun run() {
                post {
                    scrollToNextSlide()
                }
            }
        }
        timer?.schedule(sliderTask, timerDelay)
    }

    private fun stopSlider() {
        sliderTask?.cancel()
        sliderTask = null
        timer?.cancel()
        timer = null
    }

    private fun scrollToNextSlide() {
        val position = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
        if (size.get() != 0 && position != RecyclerView.NO_POSITION) {
            if (infinite && size.get() >= 3) {
                carousel.smoothScrollToPosition(position + 1)
            } else {
                //normal scrolling
                if (position + 1 < size.get()) {
                    carousel.smoothScrollToPosition(position + 1)
                } else {
                    carousel.scrollToPosition(0)
                }
            }
        }

        schedule()
    }
}