package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CrewRepoModel
import com.worldsnas.domain.servermodels.CrewServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CrewServerRepoMapper @Inject constructor(
) : Mapper<CrewServerModel, CrewRepoModel> {
    override fun map(item: CrewServerModel): CrewRepoModel =
        CrewRepoModel(
            item.creditID,
            item.department,
            item.gender,
            item.id,
            item.job,
            item.name,
            item.profilePath,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.genreIds,
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