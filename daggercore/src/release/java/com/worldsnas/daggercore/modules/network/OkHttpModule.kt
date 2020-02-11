package com.worldsnas.daggercore.modules.network

import dagger.Binds
import dagger.Module
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [InnerOkHttpModule::class])
abstract class OkHttpModule {

    @Binds
    @Singleton
    abstract fun bindOkHttp(@InnerOkHttpQualifier client : OkHttpClient) :
        OkHttpClient
}
