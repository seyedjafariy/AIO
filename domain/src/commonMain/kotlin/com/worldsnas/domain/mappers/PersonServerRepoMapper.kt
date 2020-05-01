package com.worldsnas.domain.mappers

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.PersonRepoModel
import com.worldsnas.domain.model.servermodels.PersonServerModel

class PersonServerRepoMapper : Mapper<PersonServerModel, PersonRepoModel> {
    override fun map(item: PersonServerModel): PersonRepoModel =
        PersonRepoModel(
            item.id,
            item.popularity,
            item.profilePath,
            item.name,
            item.adult
        )
}