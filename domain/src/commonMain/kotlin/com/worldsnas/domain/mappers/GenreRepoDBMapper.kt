package com.worldsnas.domain.mappers

import com.worldsnas.core.Mapper
import com.worldsnas.db.Genre
import com.worldsnas.domain.model.repomodel.GenreRepoModel

class GenreRepoDBMapper : Mapper<GenreRepoModel, Genre> {
    override fun map(item: GenreRepoModel): Genre =
        Genre.Impl(
            item.id,
            item.name,
            ""
        )
}