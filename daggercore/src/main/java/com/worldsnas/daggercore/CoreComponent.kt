package com.worldsnas.daggercore

import android.app.Application
import com.squareup.moshi.Moshi
import com.worldsnas.daggercore.modules.DatabaseModule
import com.worldsnas.daggercore.modules.network.NetworkModule
import com.worldsnas.daggercore.scope.AppScope
import com.worldsnas.domain.di.DomainModule
import com.worldsnas.domain.repo.home.latest.LatestMovieRepo
import com.worldsnas.domain.repo.home.trending.TrendingRepo
import dagger.BindsInstance
import dagger.Component
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@AppScope
@Component(
    modules = [NetworkModule::class,
        DomainModule::class,
        DatabaseModule::class]
)
interface CoreComponent {

    fun retrofit(): Retrofit
    fun okHttp(): OkHttpClient
    fun moshi(): Moshi
    fun store(): BoxStore
    fun latestMovieRepo() : LatestMovieRepo
    fun trendingRepo() : TrendingRepo

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun setApplication(app: Application): CoreComponent.Builder

        fun setDatabaseModule(module: DatabaseModule): CoreComponent.Builder

        fun build(): CoreComponent
    }
}