package com.worldsnas.daggercore

import android.app.Application
import com.squareup.moshi.Moshi
import com.worldsnas.daggercore.modules.network.NetworkModule
import com.worldsnas.daggercore.scope.AppScope
import com.worldsnas.domain.di.DomainModule
import dagger.BindsInstance
import dagger.Component
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@AppScope
@Component(modules = [NetworkModule::class, DomainModule::class])
interface CoreComponent {

    fun retrofit(): Retrofit
    fun okHttp(): OkHttpClient
    fun moshi(): Moshi
    fun store(): BoxStore

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bindApplication(app: Application): CoreComponent.Builder

        fun build(): CoreComponent
    }
}