package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class CrewEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("credit_id")
    val creditID: String = "",
    @NameInDb("department")
    val department: String = "",
    @NameInDb("gender")
    val gender: Int = 0,
    @NameInDb("job")
    val job: String = "",
    @NameInDb("name")
    val name: String = "",
    @NameInDb("profile_path")
    val profilePath: String = "",
    @NameInDb("original_language")
    val originalLanguage: String = "",
    @NameInDb("original_title")
    val originalTitle: String = "",
    @NameInDb("overview")
    val overview: String = "",
    @NameInDb("videos")
    val video: Boolean = false,
    @NameInDb("release_date")
    val releaseDate: String = "",
    @NameInDb("popularity")
    val popularity: Double = 0.0,
    @NameInDb("vote_average")
    val voteAverage: Double = 0.0,
    @NameInDb("vote_count")
    val voteCount: Int = 0,
    @NameInDb("title")
    val title: String = "",
    @NameInDb("adult")
    val adult: Boolean = false,
    @NameInDb("backdrop_path")
    val backdropPath: String = "",
    @NameInDb("poster_path")
    val posterPath: String = ""
) {
    lateinit var movies : ToMany<MovieEntity>
    lateinit var genres : ToMany<GenreEntity>
}