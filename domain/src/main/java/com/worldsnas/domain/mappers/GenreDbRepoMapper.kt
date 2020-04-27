package com.worldsnas.domain.mappers

import com.worldsnas.db.Genre
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class GenreDbRepoMapper @Inject constructor(
) : Mapper<Genre, GenreRepoModel> {
    override fun map(item: Genre): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.title
        )
}