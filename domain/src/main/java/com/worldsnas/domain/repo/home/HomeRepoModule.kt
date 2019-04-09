package com.worldsnas.domain.repo.home

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class HomeRepoModule {

    @Module
    companion object {
        @Provides
        fun provideRetrofit(retrofit : Retrofit) = retrofit.create<HomeAPI>()
    }
}