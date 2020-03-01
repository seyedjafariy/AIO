package com.worldsnas.aio

import android.os.Build
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.fresco.FrescoFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
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

        Stetho.initializeWithDefaults(this)
    }

    private fun initFlipper() {
        if (DEBUG &&
            FlipperUtils.shouldEnableFlipper(this) &&
            isNotRobolectricUnitTest()
        ) {
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
                CrashReporterPlugin.getInstance()
            )
            client.addPlugin(DatabasesFlipperPlugin(this))
            client.start()
        }
    }

    private fun isNotRobolectricUnitTest(): Boolean =
        "robolectric" != Build.FINGERPRINT
}