package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CountryRepoModel
import com.worldsnas.domain.servermodels.CountryServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CountryServerRepoMapper @Inject constructor(
) : Mapper<CountryServerModel, CountryRepoModel> {
    override fun map(item: CountryServerModel): CountryRepoModel =
        CountryRepoModel(
            item.iso,
            item.name
        )
}