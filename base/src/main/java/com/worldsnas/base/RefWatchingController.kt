package com.worldsnas.base

import android.os.Bundle
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.squareup.leakcanary.LeakCanary

abstract class RefWatchingController @JvmOverloads constructor(
    bundle: Bundle? = null
) : Controller(bundle) {

    private var hasExited: Boolean = false

    override fun onDestroy() {
        super.onDestroy()

        if (hasExited) {
            LeakCanary.installedRefWatcher().watch(this)
        }
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)

        hasExited = !changeType.isEnter
        if (isDestroyed) {
            LeakCanary.installedRefWatcher().watch(this)
        }
    }
}