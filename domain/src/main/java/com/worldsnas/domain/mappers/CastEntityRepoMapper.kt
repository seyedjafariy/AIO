package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CastEntity
import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.repomodel.CastRepoModel
import com.worldsnas.domain.repomodel.GenreRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CastEntityRepoMapper @Inject constructor(
    private val genreMapper: Mapper<GenreEntity, GenreRepoModel>
) : Mapper<CastEntity, CastRepoModel> {
    override fun map(item: CastEntity): CastRepoModel =
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
            item.overview,
            item.genres.map { genreMapper.map(it) }
        )
}