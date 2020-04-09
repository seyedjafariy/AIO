package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class MovieServerModel(
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "backdrop_path")
    val backdropPath: String = "",
    @Json(name = "budget")
    val budget: Long = 0,
    @Json(name = "genres")
    val genres: List<GenreServerModel> = emptyList(),
    @Json(name = "homepage")
    val homePage: String = "",
    @Json(name = "imdb_id")
    val imdbID: String = "",
    @Json(name = "original_language")
    val originalLanguage: String = "",
    @Json(name = "original_title")
    val originalTitle: String = "",
    @Json(name = "overview")
    val overview: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "poster_path")
    val posterPath: String = "",
    @Json(name = "production_companies")
    val productionCompanies: List<CompanyServerModel> = emptyList(),
    @Json(name = "production_countries")
    val productionCountries: List<CountryServerModel> = emptyList(),
    @Json(name = "release_date")
    val releaseDate: String = "",
    @Json(name = "revenue")
    val revenue: Long = 0,
    @Json(name = "runtime")
    val runtime: Int = 0,
    @Json(name = "spoken_languages")
    val spokenLanguages: List<LanguageServerModel> = emptyList(),
    @Json(name = "status")
    val status: String = "",
    @Json(name = "tagline")
    val tagLine: String = "",
    @Json(name = "title")
    val title: String = "",
    @Json(name = "video")
    val video: Boolean = false,
    @Json(name = "vote_average")
    val voteAverage: Double = 0.0,
    @Json(name = "vote_count")
    val voteCount: Long = 0,
    @Json(name = "videos")
    val videos: ResultsServerModel<VideoServerModel>? = null,
    @Json(name = "images")
    val images: FullImageServerModel? = null,
    @Json(name = "reviews")
    val reviews: ResultsServerModel<ReviewServerModel>? = null,
    @Json(name = "similar")
    val similar: ResultsServerModel<MovieServerModel>? = null,
    @Json(name = "recommendations")
    val recommendations: ResultsServerModel<MovieServerModel>? = null,
    @Json(name = "credits")
    val credits: CreditsServerModel? = null,
    @Json(name = "translations")
    val translations: TranslationListServerModel? = null,
    @Json(name = "external_ids")
    val externalIds: ExternalIdsSeverModel? = null
)