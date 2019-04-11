package com.worldsnas.domain

import com.worldsnas.domain.entity.CastEntity
import com.worldsnas.domain.entity.CompanyEntity
import com.worldsnas.domain.mappers.CastServerEntityMapper
import com.worldsnas.domain.mappers.CompanyServerEntityMapper
import com.worldsnas.domain.repo.home.HomeRepoModule
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module(includes = [HomeRepoModule::class])
abstract class DomainModule{

    @Binds
    abstract fun bindCastServerRepoMapper(mapper: CastServerEntityMapper) :
        Mapper<CastServerModel, CastEntity>

    @Binds
    abstract fun bindCompanyServerRepoMapper(mapper: CompanyServerEntityMapper) :
        Mapper<CompanyServerModel, CompanyEntity>
}