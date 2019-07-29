package com.worldsnas.aio

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.soloader.SoLoader
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.worldsnas.aio.BuildConfig.DEBUG
import com.worldsnas.base.RefWatcherProvider
import com.worldsnas.daggercore.CoreComponent
import com.worldsnas.daggercore.CoreComponentProvider
import com.worldsnas.daggercore.DaggerCoreComponent
import com.worldsnas.daggercore.modules.DatabaseModule
import io.fabric.sdk.android.Fabric
import timber.log.Timber


class AIOApp : Application(), CoreComponentProvider, RefWatcherProvider {

    private lateinit var frescoConfig: ImagePipelineConfig
    private lateinit var refWatcher: RefWatcher
    private lateinit var networkFlipperPlugin: NetworkFlipperPlugin

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
        frescoConfig = coreComponent.frescoConfig()
        networkFlipperPlugin = coreComponent.networkFlipperPlugin()

        refWatcher = LeakCanary.install(this)

        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Fresco.initialize(this, frescoConfig)

        FLog.setMinimumLoggingLevel(
            if (DEBUG)
                Log.VERBOSE
            else
                Log.ERROR
        )

        initCrashlytics()

        initFlipper()
    }

    private fun initCrashlytics() {
        val crashlyticsCore = CrashlyticsCore
            .Builder()
            .disabled(DEBUG)
            .build()

        Fabric.with(
            this,
            Crashlytics.Builder()
                .core(crashlyticsCore)
                .build()
        )
    }

    private fun initFlipper() {
        if (DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            SoLoader.init(this, false)

            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(
                InspectorFlipperPlugin(
                    this,
                    DescriptorMapping.withDefaults()
                )
            )
            client.addPlugin(
                networkFlipperPlugin
            )
            client.addPlugin(
                FrescoFlipperPlugin()
            )
            client.addPlugin(
                SharedPreferencesFlipperPlugin(this, this.packageName)
            )
            client.addPlugin(
                LeakCanaryFlipperPlugin()
            )
            client.addPlugin(
                CrashReporterPlugin.getInstance()
            )
            client.start()
        }
    }
}