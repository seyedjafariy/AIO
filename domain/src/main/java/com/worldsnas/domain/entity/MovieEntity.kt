package com.worldsnas.domain.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.NameInDb
import io.objectbox.relation.ToMany

@Entity
data class MovieEntity(
    @Id(assignable = true)
    var id: Long = 0,
    @NameInDb("adult")
    val adult: Boolean = false,
    @NameInDb("backdrop_path")
    val backdropPath: String = "",
    @NameInDb("budget")
    val budget: Long = 0,
    @NameInDb("home_page")
    val homePage: String = "",
    @NameInDb("imdb_id")
    val imdbID: String = "",
    @NameInDb("original_language")
    val originalLanguage: String = "",
    @NameInDb("original_title")
    val originalTitle: String = "",
    @NameInDb("override")
    val overview: String = "",
    @NameInDb("popularity")
    val popularity: Double = 0.0,
    @NameInDb("poster_path")
    val posterPath: String = "",
    @NameInDb("release_date")
    val releaseDate: String = "",
    @NameInDb("revenue")
    val revenue: Long = 0,
    @NameInDb("runtime")
    val runtime: Int = 0,
    @NameInDb("status")
    val status: String = "",
    @NameInDb("tag_line")
    val tagLine: String = "",
    @NameInDb("title")
    val title: String = "",
    @NameInDb("video")
    val video: Boolean = false,
    @NameInDb("vote_average")
    val voteAverage: Double = 0.0,
    @NameInDb("vote_count")
    val voteCount: Int = 0,
    @NameInDb("facebook_id")
    val facebookId: String = "",
    @NameInDb("instagram_id")
    val instagramId: String = "",
    @NameInDb("twitter_id")
    val twitterId: String = ""
) {
    lateinit var translations: ToMany<TranslationEntity>
    lateinit var productionCompanies: ToMany<CompanyEntity>
    lateinit var similar: ToMany<MovieEntity>
    lateinit var recommendations: ToMany<MovieEntity>
    lateinit var casts: ToMany<CastEntity>
    lateinit var crews: ToMany<CrewEntity>
    lateinit var productionCountries: ToMany<CountryEntity>
    lateinit var spokenLanguages: ToMany<LanguageEntity>
    lateinit var genres: ToMany<GenreEntity>
    lateinit var videos: ToMany<VideoEntity>
    lateinit var backdrops: ToMany<ImageEntity>
    lateinit var posters: ToMany<ImageEntity>
    lateinit var reviews: ToMany<ReviewEntity>
}