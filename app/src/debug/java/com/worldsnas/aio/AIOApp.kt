package com.worldsnas.aio

import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.worldsnas.aio.BuildConfig.DEBUG
import timber.log.Timber

class AIOApp : BaseApp() {

    private lateinit var networkFlipperPlugin: NetworkFlipperPlugin

    override fun onCreate() {
        super.onCreate()
        networkFlipperPlugin = coreComponent.networkFlipperPlugin()


        if (DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initFlipper()
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