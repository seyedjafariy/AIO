package com.worldsnas.core.di

import com.squareup.moshi.Moshi
import com.worldsnas.core.di.modules.network.NetworkModule
import com.worldsnas.core.di.scope.AppScope
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [NetworkModule::class])
interface CoreComponent {

    fun retrofit() : Retrofit
    fun okHttp() : OkHttpClient
    fun moshi(): Moshi

}