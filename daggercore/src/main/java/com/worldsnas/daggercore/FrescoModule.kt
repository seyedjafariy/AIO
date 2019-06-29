package com.worldsnas.daggercore

import android.app.Application
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestLoggingListener
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
object FrescoModule {

    @JvmStatic
    @Provides
    fun provideFrescoConfig(app: Application, okHttp: OkHttpClient): ImagePipelineConfig =
        OkHttpImagePipelineConfigFactory
            .newBuilder(app, okHttp.newBuilder().cache(null).build())
            .setDownsampleEnabled(true)
            .setRequestListeners(setOf(RequestLoggingListener()))
            .build()
}