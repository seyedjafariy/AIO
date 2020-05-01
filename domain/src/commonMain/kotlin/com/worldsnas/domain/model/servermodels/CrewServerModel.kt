package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CrewServerModel(
    @SerialName(value = "credit_id")
    val creditID: String = "",
    @SerialName(value = "department")
    val department: String = "",
    @SerialName(value = "gender")
    val gender: Int = 0,
    @SerialName(value = "id")
    val id: Long = 0,
    @SerialName(value = "job")
    val job: String = "",
    @SerialName(value = "name")
    val name: String = "",
    @SerialName(value = "profile_path")
    val profilePath: String = "",
    @SerialName(value = "original_language")
    val originalLanguage: String = "",
    @SerialName(value = "original_title")
    val originalTitle: String = "",
    @SerialName(value = "overview")
    val overview: String = "",
    @SerialName(value = "genre_ids")
    val genreIds: LongArray = longArrayOf(),
    @SerialName(value = "video")
    val video: Boolean = false,
    @SerialName(value = "release_date")
    val releaseDate: String = "",
    @SerialName(value = "popularity")
    val popularity: Double = 0.0,
    @SerialName(value = "vote_average")
    val voteAverage: Double = 0.0,
    @SerialName(value = "vote_count")
    val voteCount: Int = 0,
    @SerialName(value = "title")
    val title: String = "",
    @SerialName(value = "adult")
    val adult: Boolean = false,
    @SerialName(value = "backdrop_path")
    val backdropPath: String = "",
    @SerialName(value = "poster_path")
    val posterPath: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

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