package com.daimajia.slider.library

import android.os.Handler
import com.airbnb.epoxy.EpoxyAsyncUtil.MAIN_THREAD_HANDLER
import com.airbnb.epoxy.EpoxyAsyncUtil.getAsyncBackgroundHandler
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.IllegalEpoxyUsage

open class AsyncSimpleController @JvmOverloads constructor(
        enableAsyncModelBuilding: Boolean = true,
        enableAsyncDiffing: Boolean = true
    ) : EpoxyController(
        getHandler(enableAsyncModelBuilding),
        getHandler(enableAsyncDiffing)
    ) {
        private var currentModels: List<EpoxyModel<*>?>? = null
        private var insideSetModels = false

        /**
         * Set the models to add to this controller. Clears any previous models and adds this new list
         * .
         */
        fun setModels(models: List<EpoxyModel<*>?>?) {
            currentModels = models
            insideSetModels = true
            requestModelBuild()
            insideSetModels = false
        }

        override fun requestModelBuild() {
            if (!insideSetModels) {
                throw IllegalEpoxyUsage(
                    "You cannot call `requestModelBuild` directly. Call `setModels` instead."
                )
            }
            super.requestModelBuild()
        }

        override fun buildModels() {
            if (!isBuildingModels) {
                throw IllegalEpoxyUsage(
                    "You cannot call `buildModels` directly. Call `setModels` instead."
                )
            }
            add(currentModels!!)
        }

        companion object {
            private fun getHandler(enableAsync: Boolean): Handler? {
                return if (enableAsync) getAsyncBackgroundHandler() else MAIN_THREAD_HANDLER
            }
        }
    }