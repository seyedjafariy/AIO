package com.worldsnas.daggercore.modules.domain

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.PersonServerModel
import com.worldsnas.domain.repo.people.PeopleAPI
import com.worldsnas.domain.repo.people.PeopleAPIImpl
import com.worldsnas.domain.repo.people.PeopleRepo
import com.worldsnas.domain.repo.people.PeopleRepoImpl
import dagger.Module
import dagger.Provides
import io.ktor.client.engine.HttpClientEngine

@Module
object PeopleRepoModule {

    @JvmStatic
    @Provides
    fun bindPeopleRepo(
        api: PeopleAPI,
        personMapper: Mapper<PersonServerModel, PersonRepoModel>
    ): PeopleRepo = PeopleRepoImpl(
        api, personMapper
    )

    @JvmStatic
    @Provides
    fun providePeopleAPI(engine: HttpClientEngine): PeopleAPI =
        PeopleAPIImpl(engine)
}