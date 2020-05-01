package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.CountryRepoModel
import com.worldsnas.domain.model.servermodels.CountryServerModel

class CountryServerRepoMapper : Mapper<CountryServerModel, CountryRepoModel> {
    override fun map(item: CountryServerModel): CountryRepoModel =
        CountryRepoModel(
            0,
            item.iso,
            item.name
        )
}