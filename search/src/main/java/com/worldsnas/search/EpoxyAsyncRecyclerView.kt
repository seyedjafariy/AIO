package com.worldsnas.search

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.AsyncEpoxyController
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyRecyclerView

class EpoxyAsyncRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): EpoxyRecyclerView(context, attrs, defStyleAttr) {

    private var internalController : EpoxyController? = null

    fun withModelsAsync(buildModels: EpoxyController.() -> Unit) {
        val controller = (internalController as? WithModelsAsyncController)
            ?: WithModelsAsyncController().also {
                internalController = it
                setController(it)
            }

        controller.callback = buildModels
        controller.requestModelBuild()
    }

    private class WithModelsAsyncController : AsyncEpoxyController() {
        var callback: EpoxyController.() -> Unit = {}

        override fun buildModels() {
            callback(this)
        }
    }
}