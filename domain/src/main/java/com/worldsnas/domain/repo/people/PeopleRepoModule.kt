package com.worldsnas.domain.repo.people

import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.model.servermodels.ResultsServerModel
import com.worldsnas.panther.RFetcher
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

    @Binds
    abstract fun bindPeopleFetcher(
        fetcher : PeopleFetcher
    ): RFetcher<PeopleRequestModel, ResultsServerModel<PersonServerModel>>

    @Module
    companion object{
        @JvmStatic
        @Provides
        fun providePeopleAPI(retrofit: Retrofit) : PeopleAPI =
                retrofit.create()
    }
}