package com.worldsnas.navigation

import android.content.Intent
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.bluelinelabs.conductor.Controller

object Navigation {

    @JvmStatic
    fun createController(controller: Screens): Controller =
        if (controller.extras != null) {
            Class.forName(controller.name)
                .constructors
                .first()
                .newInstance(controller.extras) as Controller

            //better to be called using kotlin reflection
            // Class.forName(controller.name)
            // .kotlin
            // .constructors
            // .first{
            //     it.parameters.isNotEmpty()
            // }
            //     .call(controller.extras) as Controller
        } else {
            Class.forName(controller.name)
                .constructors
                .first()
                .newInstance() as Controller
        }

    @JvmStatic
    fun createIntent(activity: Activities): Intent =
        Intent(Intent.ACTION_VIEW).setClassName(
            "com.worldsnas.aio",
            activity.name
        ).apply {
            if (activity.extras != null) {
                putExtras(activity.extras)
            }
        }
}

sealed class Screens(val name: String, val extras: Bundle? = null) {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    internal object Test : Screens("com.worldsnas.navigation.TestController")

    object Home : Screens("com.worldsnas.home.HomeView")
}

sealed class Activities(val name: String, val extras: Bundle? = null) {

    object Main : Activities("com.worldsnas.aio.MainActivity")
}