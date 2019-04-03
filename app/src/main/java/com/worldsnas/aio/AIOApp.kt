package com.worldsnas.aio

import android.app.Application
import com.worldsnas.aio.di.DaggerAppComponent
import com.worldsnas.core.di.CoreComponent
import com.worldsnas.core.di.CoreComponentProvider

class AIOApp : Application(), CoreComponentProvider {

    private val coreComponent by lazy {
        DaggerAppComponent.builder().build()
    }

    override fun core(): CoreComponent =
        coreComponent
}