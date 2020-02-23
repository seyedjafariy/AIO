package com.worldsnas.daggercore.modules.network

import com.squareup.moshi.Moshi
import com.worldsnas.domain.model.servermodels.AppJsonAdapterFactory
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [OkHttpModule::class])
object NetworkModule {

    @JvmStatic
    @Provides
    fun provideTypeAdapter(): Moshi {
        val builder = Moshi.Builder()
        builder.add(AppJsonAdapterFactory.INSTANCE)
        return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideInvestRetrofit2Helper(client: Lazy<OkHttpClient>, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .callFactory(object : Call.Factory {
                override fun newCall(request: Request): Call {
                    return client.get().newCall(request)
                }
            })
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}
