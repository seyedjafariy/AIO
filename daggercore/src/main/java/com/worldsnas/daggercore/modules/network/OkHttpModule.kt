package com.worldsnas.daggercore.modules.network

import android.app.Application
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
        val httpLoggingInterceptor = HttpLoggingInterceptor { message -> Timber.d(message) }

        httpLoggingInterceptor.level = if (DEBUG) BODY else NONE

        return httpLoggingInterceptor
    }

    @JvmStatic
    @Provides
    fun provideOkhttpCache(app: Application): Cache =
        Cache(app.cacheDir, 50_000_000)

    @JvmStatic
    @Provides
    @Singleton
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        protocolInterceptor: NoContentProtocolExceptionInterceptor,
        authInterceptor: AuthTokenAdderInterceptor,
        cache: Cache
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(120, TimeUnit.SECONDS)// Set connection timeout
            .readTimeout(120, TimeUnit.SECONDS)// Read timeout
            .writeTimeout(120, TimeUnit.SECONDS)// Write timeout
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(protocolInterceptor)
            .addInterceptor(authInterceptor)
        return builder.build()
    }
}
