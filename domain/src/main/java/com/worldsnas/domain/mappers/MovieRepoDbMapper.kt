package com.worldsnas.domain.mappers

import com.worldsnas.db.Movie
import com.worldsnas.domain.helpers.toDate
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieRepoDbMapper @Inject constructor(
) : Mapper<MovieRepoModel, Movie> {
    override fun map(item: MovieRepoModel): Movie =
        Movie.Impl(
            item.id,
            item.title,
            item.adult,
            item.originalTitle,
            item.budget,
            item.homePage,
            item.imdbID,
            item.facebookId,
            item.instagramId,
            item.twitterId,
            item.originalLanguage,
            item.overview,
            item.popularity,
            item.backdropPath,
            item.posterPath,
            item.releaseDate.toDate(),
            item.revenue,
            item.runtime.toLong(),
            item.status,
            item.tagLine,
            item.video,
            item.voteAverage,
            item.voteCount,
            "",
            false
        )
}