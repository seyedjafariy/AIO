package com.worldsnas.daggercore.modules

import android.app.Application
import com.worldsnas.domain.entity.MyObjectBox
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application) =
        MyObjectBox
            .builder()
            .androidContext(context)
            .build()
}