package com.worldsnas.domain.mappers.server

import com.worldsnas.core.Mapper
import com.worldsnas.domain.helpers.ImageType
import com.worldsnas.domain.model.ImageServerTypeHolder
import com.worldsnas.domain.model.repomodel.*
import com.worldsnas.domain.model.servermodels.*

class MovieServerRepoMapper(
    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel>,
    private val companyMapper: Mapper<CompanyServerModel, CompanyRepoModel>,
    private val countryMapper: Mapper<CountryServerModel, CountryRepoModel>,
    private val languageMapper: Mapper<LanguageServerModel, LanguageRepoModel>,
    private val videoMapper: Mapper<VideoServerModel, VideoRepoModel>,
    private val imageMapper: Mapper<ImageServerTypeHolder, ImageRepoModel>,
    private val reviewMapper: Mapper<ReviewServerModel, ReviewRepoModel>,
    private val castMapper: Mapper<CastServerModel, CastRepoModel>,
    private val crewMapper: Mapper<CrewServerModel, CrewRepoModel>,
    private val translationMapper: Mapper<TranslationServerModel, TranslationRepoModel>
) : Mapper<MovieServerModel, MovieRepoModel> {
    override fun map(item: MovieServerModel): MovieRepoModel =
        MovieRepoModel(
            item.id,
            item.adult,
            item.backdropPath ?: "",
            item.budget,
            item.homePage,
            item.imdbID,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.popularity,
            item.posterPath ?: "",
            item.releaseDate,
            item.revenue,
            item.runtime,
            item.status,
            item.tagLine,
            item.title,
            item.video,
            item.voteAverage,
            item.voteCount,
            item.externalIds?.facebookId ?: "",
            item.externalIds?.instagramId ?: "",
            item.externalIds?.twitterId ?: "",
            item.spokenLanguages.map { languageMapper.map(it) },
            item.productionCompanies.map { companyMapper.map(it) },
            item.productionCountries.map { countryMapper.map(it) },
            item.genres.map { genreMapper.map(it) },
            item.videos?.list?.map { videoMapper.map(it) } ?: emptyList(),
            item.images?.backdrops?.map {
                val holder = ImageServerTypeHolder(it, ImageType.Backdrop)
                imageMapper.map(holder)
            } ?: emptyList(),
            item.images?.posters?.map {
                val holder = ImageServerTypeHolder(it, ImageType.Poster)
                imageMapper.map(holder)
            } ?: emptyList(),
            item.reviews?.list?.map { reviewMapper.map(it) } ?: emptyList(),
            item.similar?.list?.map { map(it) } ?: emptyList(),
            item.recommendations?.list?.map { map(it) } ?: emptyList(),
            item.credits?.casts?.map { castMapper.map(it) } ?: emptyList(),
            item.credits?.crews?.map { crewMapper.map(it) } ?: emptyList(),
            item.translations?.translations?.map { translationMapper.map(it) } ?: emptyList()
        )
}