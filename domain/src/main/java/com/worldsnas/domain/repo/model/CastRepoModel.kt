package com.worldsnas.domain.repo.model


data class CastRepoModel (
    val castID: Int = 0,
    val character: String = "",
    val creditId: String = "",
    val gender: Int = 0,
    val id: Long = 0,
    val name: String = "",
    val order: Int = 0,
    val profilePath: String = "",
    val posterPath: String = "",
    val adult: Boolean = false,
    val backdropPath: String = "",
    val voteCount: Int = 0,
    val video: Boolean = false,
    val popularity: Double = 0.0,
    val genreIds: IntArray = intArrayOf(),
    val originalLanguage: String = "",
    val title: String = "",
    val originalTitle: String = "",
    val releaseDate: String = "",
    val voteAverage: Double = 0.0,
    val overview: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CastRepoModel

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