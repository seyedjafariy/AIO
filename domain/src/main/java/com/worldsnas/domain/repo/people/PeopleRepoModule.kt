package com.worldsnas.domain.repo.people

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

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
        fun providePeopleAPI(engine: HttpClientEngine) : PeopleAPI =
            PeopleAPIImpl(engine)
    }
}