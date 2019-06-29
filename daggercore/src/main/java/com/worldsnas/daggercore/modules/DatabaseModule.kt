package com.worldsnas.daggercore.modules

import android.app.Application
import com.worldsnas.domain.entity.MyObjectBox
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Application): BoxStore =
        MyObjectBox
            .builder()
            .androidContext(context)
            .build()
}