package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.model.repomodel.CrewRepoModel
import com.worldsnas.domain.model.servermodels.CrewServerModel
import com.worldsnas.core.Mapper
import javax.inject.Inject

class CrewServerRepoMapper @Inject constructor(
): Mapper<CrewServerModel, CrewRepoModel> {
    override fun map(item: CrewServerModel): CrewRepoModel =
        CrewRepoModel(
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
        )
}