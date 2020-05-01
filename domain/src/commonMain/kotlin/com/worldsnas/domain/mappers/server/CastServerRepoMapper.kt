package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.model.repomodel.CastRepoModel
import com.worldsnas.domain.model.servermodels.CastServerModel

class CastServerRepoMapper : Mapper<CastServerModel, CastRepoModel> {
    override fun map(item: CastServerModel): CastRepoModel =
        CastRepoModel(
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
        )
}