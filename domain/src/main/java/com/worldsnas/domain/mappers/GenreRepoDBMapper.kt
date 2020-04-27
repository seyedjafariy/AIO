package com.worldsnas.domain.mappers

import com.worldsnas.db.Genre
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class GenreRepoDBMapper @Inject constructor(
) : Mapper<GenreRepoModel, Genre> {
    override fun map(item: GenreRepoModel): Genre =
        Genre.Impl(
            item.id,
            item.name,
            ""
        )
}