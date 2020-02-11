package com.worldsnas.daggercore.modules.network

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.worldsnas.daggercore.BuildConfig.DEBUG
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object OkHttpModule {

    @JvmStatic
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })

        httpLoggingInterceptor.level = if (DEBUG) BODY else NONE

        return httpLoggingInterceptor
    }

    @JvmStatic
    @Provides
    fun provideOkhttpCache(app: Application): Cache =
        Cache(app.cacheDir, 50_000_000)

    @JvmStatic
    @Provides
    fun provideChucker(app: Application) =
        ChuckerInterceptor(app)


    @JvmStatic
    @Provides
    @Singleton
    fun provideFlipperPlugin(): NetworkFlipperPlugin =
        NetworkFlipperPlugin()

    @JvmStatic
    @Provides
    @Singleton
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        protocolInterceptor: NoContentProtocolExceptionInterceptor,
        authInterceptor: AuthTokenAdderInterceptor,
        cache: Cache,
        flipperPlugin: NetworkFlipperPlugin,
        chucker: ChuckerInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(120, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(120, TimeUnit.SECONDS)// Write timeout
//            .cache(cache)
            .addInterceptor(chucker)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(protocolInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(FlipperOkhttpInterceptor(flipperPlugin))
            .build()
    }
}
