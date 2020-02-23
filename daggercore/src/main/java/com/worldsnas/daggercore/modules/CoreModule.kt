package com.worldsnas.daggercore.modules

import android.app.Application
import com.worldsnas.androidcore.DisplaySizeFactory
import dagger.Module
import dagger.Provides

@Module
object CoreModule {

    @JvmStatic
    @Provides
    fun provideDisplaySize(context: Application, provider: DisplaySizeFactory) =
        provider.create(context)
}