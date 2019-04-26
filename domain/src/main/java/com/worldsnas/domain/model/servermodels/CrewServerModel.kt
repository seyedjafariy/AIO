package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class CrewServerModel(
    @Json(name = "credit_id")
    val creditID: String = "",
    @Json(name = "department")
    val department: String = "",
    @Json(name = "gender")
    val gender: Int = 0,
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "job")
    val job: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "profile_path")
    val profilePath: String = "",
    @Json(name = "original_language")
    val originalLanguage: String = "",
    @Json(name = "original_title")
    val originalTitle: String = "",
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "genre_ids")
    val genreIds: LongArray = longArrayOf(),
    @Json(name = "video")
    val video: Boolean = false,
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "vote_count")
    val voteCount: Int = 0,
    @Json(name = "title")
    val title: String = "",
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "backdrop_path")
    val backdropPath: String = "",
    @Json(name = "poster_path")
    val posterPath: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CrewServerModel

        if (creditID != other.creditID) return false
        if (department != other.department) return false
        if (gender != other.gender) return false
        if (id != other.id) return false
        if (job != other.job) return false
        if (name != other.name) return false
        if (profilePath != other.profilePath) return false
        if (originalLanguage != other.originalLanguage) return false
        if (originalTitle != other.originalTitle) return false
        if (overview != other.overview) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (video != other.video) return false
        if (releaseDate != other.releaseDate) return false
        if (popularity != other.popularity) return false
        if (voteAverage != other.voteAverage) return false
        if (voteCount != other.voteCount) return false
        if (title != other.title) return false
        if (adult != other.adult) return false
        if (backdropPath != other.backdropPath) return false
        if (posterPath != other.posterPath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = creditID.hashCode()
        result = 31 * result + department.hashCode()
        result = 31 * result + gender
        result = 31 * result + id.hashCode()
        result = 31 * result + job.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + profilePath.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + video.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteAverage.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + title.hashCode()
        result = 31 * result + adult.hashCode()
        result = 31 * result + backdropPath.hashCode()
        result = 31 * result + posterPath.hashCode()
        return result
    }
}