package com.worldsnas.domain.model.servermodels

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class PersonServerModel(
    @Json(name = "birthday")
    val birthday: String = "",
    @Json(name = "known_for_department")
    val knownForDepartment: String = "",
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "place_of_birth")
    val placeOfBirth: String = "",
    @Json(name = "homepage")
    val homepage: String = "",
    @Json(name = "profile_path")
    val profilePath: String = "",
    @Json(name = "imdb_id")
    val imdbId: String = "",
    @Json(name = "deathday")
    val deathDay: String = "",
    @Json(name = "name")
    val name: String = "",
    @Json(name = "adult")
    val adult: Boolean = false,
    @Json(name = "also_known_as")
    val alsoKnownAs: List<String> = emptyList(),
    @Json(name = "biography")
    val biography: String = "",
    @Json(name = "popularity")
    val popularity: Double = 0.0,
    @Json(name = "gender")
    val gender: Int = 0,
    @Json(name = "tagged_images")
    val taggedImages: ResultsServerModel<ImageServerModel>,
    @Json(name = "translations")
    val translations: TranslationListServerModel,
    @Json(name = "movie_credits")
    val movieCredits: CreditsServerModel,
    @Json(name = "tv_credits")
    val tvCredits: CreditsServerModel,
    @Json(name = "external_ids")
    val externalIds: ExternalIdsSeverModel,
    @Json(name = "images")
    val images: ImageListServerModel
)