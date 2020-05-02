package com.worldsnas.navigation

import android.app.Application
import android.os.Bundle
import com.bluelinelabs.conductor.Controller

object ControllerFactory {

    @JvmStatic
    fun createController(controller: Screens, app: Application? = null): Controller =
        app
            ?.run {
                controller.createWithContext(this)
            }
            ?: controller.createWithBundle()
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

                val extra = Bundle().apply {
                    if (extras != null) {
                        putByteArray(extras.first, extras.second)
                    }
                }

                if (needsBundle) {
                    constructor.newInstance(app, extra)
                } else {
                    constructor.newInstance(app)
                } as Controller
            }

    private fun Screens.createWithBundle(): Controller? {
        if (extras == null) {
            return null
        }

        val extra = Bundle().apply {
            putByteArray(extras.first, extras.second)
        }

        return Class.forName(name)
            .constructors
            .first()
            .newInstance(extra) as Controller
    }


    private fun Screens.createWithEmptyConstructor(): Controller =
        Class.forName(name)
            .constructors
            .first()
            .newInstance() as Controller
}