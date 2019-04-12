package com.worldsnas.domain.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CastServerModel(
    @Json(name = "cast_id")
    val castID: Int = 0,
    @Json(name = "character")
    val character: String = "",
    @Json(name = "credit_id")
    val creditId: String = "",
    @Json(name = "gender")
    val gender: Int = 0,
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "order")
    val order: Int = 0,
    @Json(name = "profile_path")
    val profilePath: String = "",
    @Json(name = "poster_path")
    val posterPath: String = "",
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "backdrop_path")
    val backdropPath: String = "",
    @Json(name = "vote_count")
    val voteCount: Int = 0,
    @Json(name = "video")
    val video: Boolean = false,
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "genre_ids")
    val genreIds: LongArray = longArrayOf(),
    @Json(name = "original_language")
    val originalLanguage: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "original_title")
    val originalTitle: String = "",
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "overview")
    val overview: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CastServerModel

        if (castID != other.castID) return false
        if (character != other.character) return false
        if (creditId != other.creditId) return false
        if (gender != other.gender) return false
        if (id != other.id) return false
        if (name != other.name) return false
        if (order != other.order) return false
        if (profilePath != other.profilePath) return false
        if (posterPath != other.posterPath) return false
        if (adult != other.adult) return false
        if (backdropPath != other.backdropPath) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (popularity != other.popularity) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (originalTitle != other.originalTitle) return false
        if (releaseDate != other.releaseDate) return false
        if (voteAverage != other.voteAverage) return false
        if (overview != other.overview) return false

        return true
    }

    override fun hashCode(): Int {
        var result = castID
        result = 31 * result + character.hashCode()
        result = 31 * result + creditId.hashCode()
        result = 31 * result + gender
        result = 31 * result + id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + order
        result = 31 * result + profilePath.hashCode()
        result = 31 * result + posterPath.hashCode()
        result = 31 * result + adult.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + video.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + overview.hashCode()
        return result
    }
}