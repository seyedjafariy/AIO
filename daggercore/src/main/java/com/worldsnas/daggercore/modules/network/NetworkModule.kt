package com.worldsnas.daggercore.modules.network

import com.squareup.moshi.Moshi
import com.worldsnas.domain.servermodels.AppJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
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
        // builder.add(ArrayListMoshiAdapter.FACTORY)
        // builder.add(com.sharifin.investment.servermodel.ApplicationJsonAdapterFactory.INSTANCE)
        return builder.build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideInvestRetrofit2Helper(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}
