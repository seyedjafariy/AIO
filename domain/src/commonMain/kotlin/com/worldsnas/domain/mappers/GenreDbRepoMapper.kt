package com.worldsnas.domain.mappers

import com.worldsnas.core.Mapper
import com.worldsnas.db.Genre
import com.worldsnas.domain.model.repomodel.GenreRepoModel

class GenreDbRepoMapper : Mapper<Genre, GenreRepoModel> {
    override fun map(item: Genre): GenreRepoModel =
        GenreRepoModel(
            item.id,
            item.title
        )
}