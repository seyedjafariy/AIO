package com.worldsnas.domain.mappers

import com.worldsnas.core.Mapper
import com.worldsnas.db.Movie
import com.worldsnas.domain.helpers.toStringDate
import com.worldsnas.domain.model.repomodel.MovieRepoModel

class MovieDbRepoMapper : Mapper<Movie, MovieRepoModel> {
    override fun map(item: Movie): MovieRepoModel =
        MovieRepoModel(
            item.id,
            item.adult,
            item.backdropImage,
            item.budget,
            item.homePage,
            item.imdbId,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.popularity,
            item.posterImage,
            item.releaseDate.toStringDate(),
            item.revenue,
            item.runtime.toInt(),
            item.status,
            item.tagLine,
            item.title,
            item.video,
            item.voteAverage,
            item.voteCount,
            item.facebookId,
            item.instagramId,
            item.twitterId
        )
}