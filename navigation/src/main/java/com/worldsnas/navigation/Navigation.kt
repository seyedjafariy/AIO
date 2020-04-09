package com.worldsnas.navigation

import android.app.Application
import android.content.Intent
import android.os.Bundle
import com.bluelinelabs.conductor.Controller

object Navigation {

    @JvmStatic
    fun createController(app: Application, controller: Screens): Controller =
        controller.createWithContext(app) ?: controller.createWithBundle()
        ?: controller.createWithEmptyConstructor()

    private fun Screens.createWithContext(app: Application): Controller? =
        Class.forName(name)
            .constructors
            .find { constructor ->
                constructor
                    .parameterTypes
                    .find { param ->
                        param.isAssignableFrom(Application::class.java)
                    } != null
            }?.let { constructor ->
                val needsBundle = constructor.parameterTypes.find { param ->
                    param.isAssignableFrom(Bundle::class.java)
                } != null

                if (needsBundle) {
                    constructor.newInstance(app, extras)
                } else {
                    constructor.newInstance(app)
                } as Controller
            }

    private fun Screens.createWithBundle(): Controller? {
        if (extras == null) {
            return null
        }

        return Class.forName(name)
            .constructors
            .first()
            .newInstance(extras) as Controller
    }


    private fun Screens.createWithEmptyConstructor(): Controller =
        Class.forName(name)
            .constructors
            .first()
            .newInstance() as Controller


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