package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class CastEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("cast_id")
    val castID: Int = 0,
    @NameInDb("character")
    val character: String = "",
    @NameInDb("credit_id")
    val creditId: String = "",
    @NameInDb("gender")
    val gender: Int = 0,
    @NameInDb("name")
    val name: String = "",
    @NameInDb("order")
    val order: Int = 0,
    @NameInDb("profile_path")
    val profilePath: String = "",
    @NameInDb("poster_path")
    val posterPath: String = "",
    @NameInDb("adult")
    val adult: Boolean = false,
    @NameInDb("backdrop_path")
    val backdropPath: String = "",
    @NameInDb("vote_count")
    val voteCount: Int = 0,
    @NameInDb("video")
    val video: Boolean = false,
    @NameInDb("popularity")
    val popularity: Double = 0.0,
    @NameInDb("original_language")
    val originalLanguage: String = "",
    @NameInDb("title")
    val title: String = "",
    @NameInDb("original_title")
    val originalTitle: String = "",
    @NameInDb("release_date")
    val releaseDate: String = "",
    @NameInDb("vote_average")
    val voteAverage: Double = 0.0,
    @NameInDb("overview")
    val overview: String = ""
) {

    lateinit var movies : ToMany<MovieEntity>
    lateinit var genres : ToMany<GenreEntity>
}