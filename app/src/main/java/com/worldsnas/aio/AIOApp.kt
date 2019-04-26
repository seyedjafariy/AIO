package com.worldsnas.aio

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.worldsnas.aio.BuildConfig.DEBUG
import com.worldsnas.base.RefWatcherProvider
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.CoreComponentProvider
import com.worldsnas.daggercore.DaggerCoreComponent
import com.worldsnas.daggercore.modules.DatabaseModule
import okhttp3.OkHttpClient
import timber.log.Timber

class AIOApp : Application(), CoreComponentProvider, RefWatcherProvider {

    private lateinit var okHttpClient: OkHttpClient
    private lateinit var refWatcher: RefWatcher

    private val coreComponent by lazy {
        DaggerCoreComponent
            .builder()
            .setApplication(this)
            .setDatabaseModule(DatabaseModule())
            .build()
    }

    override fun core(): CoreComponent =
        coreComponent

    override fun getRefWatcher(): RefWatcher =
        refWatcher

    override fun onCreate() {
        super.onCreate()
        okHttpClient = coreComponent.okHttp()
        refWatcher = LeakCanary.install(this)

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val config = OkHttpImagePipelineConfigFactory
            .newBuilder(this, okHttpClient)
            .setDownsampleEnabled(true)
            // .setRequestListeners()
            .build()

        Fresco.initialize(this, config)
    }
}