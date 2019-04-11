package com.worldsnas.domain

import com.worldsnas.domain.repo.home.HomeRepoModule
import com.worldsnas.domain.repo.mappers.CastServerRepoMapper
import com.worldsnas.domain.repo.mappers.CompanyServerRepoMapper
import com.worldsnas.domain.repo.model.CastRepoModel
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module(includes = [HomeRepoModule::class])
abstract class DomainModule{

    @Binds
    abstract fun bindCastServerRepoMapper(mapper: CastServerRepoMapper) :
        Mapper<CastServerModel, CastRepoModel>

    @Binds
    abstract fun bindCompanyServerRepoMapper(mapper: CompanyServerRepoMapper) :
        Mapper<CastServerModel, CastRepoModel>
}