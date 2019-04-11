package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CrewEntity
import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.repomodel.CrewRepoModel
import com.worldsnas.domain.repomodel.GenreRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class CrewEntityRepoMapper @Inject constructor(
    private val genreMapper : Mapper<GenreEntity, GenreRepoModel>
) : Mapper<CrewEntity, CrewRepoModel> {
    override fun map(item: CrewEntity): CrewRepoModel =
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
            item.posterPath,
            item.genres.map { genreMapper.map(it) }
        )
}