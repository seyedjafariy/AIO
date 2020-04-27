package com.worldsnas.domain.repo.people

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
abstract class PeopleRepoModule {

    @Binds
    abstract fun bindPeopleRepo(
        repo : PeopleRepoImpl
    ): PeopleRepo

    @Module
    companion object{
        @JvmStatic
        @Provides
        fun providePeopleAPI(retrofit: Retrofit) : PeopleAPI =
                retrofit.create()
    }
}