package com.worldsnas.domain.repomodel


data class CrewRepoModel(
    val creditID: String = "",
    val department: String = "",
    val gender: Int = 0,
    val id: Long = 0,
    val job: String = "",
    val name: String = "",
    val profilePath: String = "",
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val genreIds: IntArray = intArrayOf(),
    val video: Boolean = false,
    val releaseDate: String = "",
    val popularity: Double = 0.0,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
    val title: String = "",
    val adult: Boolean = false,
    val backdropPath: String = "",
    val posterPath: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CrewRepoModel

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