package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CrewEntity
import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.model.servermodels.CrewServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CrewServerEntityMapper @Inject constructor(
) : Mapper<CrewServerModel, CrewEntity> {
    override fun map(item: CrewServerModel): CrewEntity =
        CrewEntity(
            item.id,
            item.creditID,
            item.department,
            item.gender,
            item.job,
            item.name,
            item.profilePath,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.video,
            item.releaseDate,
            item.popularity,
            item.voteAverage,
            item.voteCount,
            item.title,
            item.adult,
            item.backdropPath,
            item.posterPath
        ).apply {
            genres.addAll(item.genreIds.map { GenreEntity(it) })
        }
}