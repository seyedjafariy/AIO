package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CastEntity
import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CastServerEntityMapper @Inject constructor(
) : Mapper<CastServerModel, CastEntity> {
    override fun map(item: CastServerModel): CastEntity =
        CastEntity(
            item.id,
            item.castID,
            item.character,
            item.creditId,
            item.gender,
            item.name,
            item.order,
            item.profilePath,
            item.posterPath,
            item.adult,
            item.backdropPath,
            item.voteCount,
            item.video,
            item.popularity,
            item.originalLanguage,
            item.title,
            item.originalTitle,
            item.releaseDate,
            item.voteAverage,
            item.overview
        ).apply {
            genres.addAll(item.genreIds.map { GenreEntity(it) })
        }
}