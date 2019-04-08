package com.worldsnas.aio

import android.app.Application
import com.worldsnas.aio.BuildConfig.DEBUG
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.CoreComponentProvider
import com.worldsnas.daggercore.DaggerCoreComponent
import timber.log.Timber

class AIOApp : Application(), CoreComponentProvider {

    private val coreComponent by lazy {
        DaggerCoreComponent.builder().build()
    }

    override fun core(): CoreComponent =
        coreComponent

    override fun onCreate() {
        super.onCreate()

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}