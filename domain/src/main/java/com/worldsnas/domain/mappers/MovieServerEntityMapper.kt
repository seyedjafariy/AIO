package com.worldsnas.domain.mappers

import com.worldsnas.domain.entity.CastEntity
import com.worldsnas.domain.entity.CompanyEntity
import com.worldsnas.domain.entity.CountryEntity
import com.worldsnas.domain.entity.CrewEntity
import com.worldsnas.domain.entity.GenreEntity
import com.worldsnas.domain.entity.ImageEntity
import com.worldsnas.domain.entity.LanguageEntity
import com.worldsnas.domain.entity.MovieEntity
import com.worldsnas.domain.entity.ReviewEntity
import com.worldsnas.domain.entity.TranslationEntity
import com.worldsnas.domain.entity.VideoEntity
import com.worldsnas.domain.model.servermodels.CastServerModel
import com.worldsnas.domain.model.servermodels.CompanyServerModel
import com.worldsnas.domain.model.servermodels.CountryServerModel
import com.worldsnas.domain.model.servermodels.CrewServerModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.domain.model.servermodels.ImageServerModel
import com.worldsnas.domain.model.servermodels.LanguageServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ReviewServerModel
import com.worldsnas.domain.model.servermodels.TranslationServerModel
import com.worldsnas.domain.model.servermodels.VideoServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieServerEntityMapper @Inject constructor(
    private val genreMapper: Mapper<GenreServerModel, GenreEntity>,
    private val companyMapper: Mapper<CompanyServerModel, CompanyEntity>,
    private val countryMapper: Mapper<CountryServerModel, CountryEntity>,
    private val languageMapper: Mapper<LanguageServerModel, LanguageEntity>,
    private val videoMapper: Mapper<VideoServerModel, VideoEntity>,
    private val imageMapper: Mapper<ImageServerModel, ImageEntity>,
    private val reviewMapper: Mapper<ReviewServerModel, ReviewEntity>,
    private val castMapper: Mapper<CastServerModel, CastEntity>,
    private val crewMapper: Mapper<CrewServerModel, CrewEntity>,
    private val translationMapper: Mapper<TranslationServerModel, TranslationEntity>
) : Mapper<MovieServerModel, MovieEntity> {

    override fun map(item: MovieServerModel): MovieEntity =
        MovieEntity(
            item.id,
            item.adult,
            item.backdropPath,
            item.budget,
            item.homePage,
            item.imdbID,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.popularity,
            item.posterPath,
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
            item.externalIds?.twitterId ?: ""
        ).apply {
            id = 0
            genres.addAll(item.genres.map { genreMapper.map(it) })
            productionCompanies.addAll(item.productionCompanies.map { companyMapper.map(it) })
            productionCountries.addAll(item.productionCountries.map { countryMapper.map(it) })
            spokenLanguages.addAll(item.spokenLanguages.map { languageMapper.map(it) })
            videos.addAll(item.videos?.list?.map { videoMapper.map(it) } ?: emptyList())
            backdrops.addAll(item.images?.backdrops?.map { imageMapper.map(it) } ?: emptyList())
            posters.addAll(item.images?.posters?.map { imageMapper.map(it) } ?: emptyList())
            reviews.addAll(item.reviews?.list?.map { reviewMapper.map(it) } ?: emptyList())
            similar.addAll(item.similar?.list?.map { map(it) } ?: emptyList())
            recommendations.addAll(item.recommendations?.list?.map { map(it) } ?: emptyList())
            casts.addAll(item.credits?.casts?.map { castMapper.map(it) } ?: emptyList())
            crews.addAll(item.credits?.crews?.map { crewMapper.map(it) } ?: emptyList())
            translations.addAll(item.translations?.translations?.map { translationMapper.map(it) } ?: emptyList())
            id = item.id
        }
}
