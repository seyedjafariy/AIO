package com.worldsnas.daggercore.modules

import android.app.Application
import com.worldsnas.core.DisplaySize
import com.worldsnas.core.DisplaySizeFactory
import dagger.Module

@Module
class CoreModule {

    fun provideDisplaySize(context: Application, provider: DisplaySizeFactory): DisplaySize =
        provider.create(context)
}