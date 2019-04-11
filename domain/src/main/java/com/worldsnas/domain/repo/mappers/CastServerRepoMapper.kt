package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CastRepoModel
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CastServerRepoMapper @Inject constructor(
): Mapper<CastServerModel, CastRepoModel> {
    override fun map(item: CastServerModel): CastRepoModel =
        CastRepoModel(
            item.castID,
            item.character,
            item.creditId,
            item.gender,
            item.id,
            item.name,
            item.order,
            item.profilePath,
            item.posterPath,
            item.adult,
            item.backdropPath,
            item.voteCount,
            item.video,
            item.popularity,
            item.genreIds,
            item.originalLanguage,
            item.title,
            item.originalTitle,
            item.releaseDate,
            item.voteAverage,
            item.overview
        )
}