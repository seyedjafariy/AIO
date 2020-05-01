package com.worldsnas.domain.model.servermodels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieServerModel(
    @SerialName(value = "id")
    val id: Long = 0,
    @SerialName(value = "adult")
    val adult: Boolean = false,
    @SerialName(value = "backdrop_path")
    val backdropPath: String? = "",
    @SerialName(value = "budget")
    val budget: Long = 0,
    @SerialName(value = "genres")
    val genres: List<GenreServerModel> = emptyList(),
    @SerialName(value = "homepage")
    val homePage: String = "",
    @SerialName(value = "imdb_id")
    val imdbID: String = "",
    @SerialName(value = "original_language")
    val originalLanguage: String = "",
    @SerialName(value = "original_title")
    val originalTitle: String = "",
    @SerialName(value = "overview")
    val overview: String = "",
    @SerialName(value = "popularity")
    val popularity: Double = 0.0,
    @SerialName(value = "poster_path")
    val posterPath: String? = "",
    @SerialName(value = "production_companies")
    val productionCompanies: List<CompanyServerModel> = emptyList(),
    @SerialName(value = "production_countries")
    val productionCountries: List<CountryServerModel> = emptyList(),
    @SerialName(value = "release_date")
    val releaseDate: String = "",
    @SerialName(value = "revenue")
    val revenue: Long = 0,
    @SerialName(value = "runtime")
    val runtime: Int = 0,
    @SerialName(value = "spoken_languages")
    val spokenLanguages: List<LanguageServerModel> = emptyList(),
    @SerialName(value = "status")
    val status: String = "",
    @SerialName(value = "tagline")
    val tagLine: String = "",
    @SerialName(value = "title")
    val title: String = "",
    @SerialName(value = "video")
    val video: Boolean = false,
    @SerialName(value = "vote_average")
    val voteAverage: Double = 0.0,
    @SerialName(value = "vote_count")
    val voteCount: Long = 0,
    @SerialName(value = "videos")
    val videos: ResultsServerModel<VideoServerModel>? = null,
    @SerialName(value = "images")
    val images: FullImageServerModel? = null,
    @SerialName(value = "reviews")
    val reviews: ResultsServerModel<ReviewServerModel>? = null,
    @SerialName(value = "similar")
    val similar: ResultsServerModel<MovieServerModel>? = null,
    @SerialName(value = "recommendations")
    val recommendations: ResultsServerModel<MovieServerModel>? = null,
    @SerialName(value = "credits")
    val credits: CreditsServerModel? = null,
    @SerialName(value = "translations")
    val translations: TranslationListServerModel? = null,
    @SerialName(value = "external_ids")
    val externalIds: ExternalIdsSeverModel? = null
)