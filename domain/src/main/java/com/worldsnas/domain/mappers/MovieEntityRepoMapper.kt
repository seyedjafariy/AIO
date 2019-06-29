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
import com.worldsnas.domain.model.repomodel.CastRepoModel
import com.worldsnas.domain.model.repomodel.CompanyRepoModel
import com.worldsnas.domain.model.repomodel.CountryRepoModel
import com.worldsnas.domain.model.repomodel.CrewRepoModel
import com.worldsnas.domain.model.repomodel.GenreRepoModel
import com.worldsnas.domain.model.repomodel.ImageRepoModel
import com.worldsnas.domain.model.repomodel.LanguageRepoModel
import com.worldsnas.domain.model.repomodel.MovieRepoModel
import com.worldsnas.domain.model.repomodel.ReviewRepoModel
import com.worldsnas.domain.model.repomodel.TranslationRepoModel
import com.worldsnas.domain.model.repomodel.VideoRepoModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieEntityRepoMapper @Inject constructor(
    private val genreMapper: Mapper<GenreEntity, GenreRepoModel>,
    private val companyMapper: Mapper<CompanyEntity, CompanyRepoModel>,
    private val countryMapper: Mapper<CountryEntity, CountryRepoModel>,
    private val languageMapper: Mapper<LanguageEntity, LanguageRepoModel>,
    private val videoMapper: Mapper<VideoEntity, VideoRepoModel>,
    private val imageMapper: Mapper<ImageEntity, ImageRepoModel>,
    private val reviewMapper: Mapper<ReviewEntity, ReviewRepoModel>,
    private val castMapper: Mapper<CastEntity, CastRepoModel>,
    private val crewMapper: Mapper<CrewEntity, CrewRepoModel>,
    private val translationMapper: Mapper<TranslationEntity, TranslationRepoModel>
) : Mapper<MovieEntity, MovieRepoModel> {

    override fun map(item: MovieEntity): MovieRepoModel =
        MovieRepoModel(
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
            item.facebookId,
            item.instagramId,
            item.twitterId,
            item.spokenLanguages.map { languageMapper.map(it) },
            item.productionCompanies.map { companyMapper.map(it) },
            item.productionCountries.map { countryMapper.map(it) },
            item.genres.map { genreMapper.map(it) },
            item.videos.map { videoMapper.map(it) },
            item.backdrops.map { imageMapper.map(it) },
            item.posters.map { imageMapper.map(it) },
            item.reviews.map { reviewMapper.map(it) },
            item.similar.map { map(it) },
            item.recommendations.map { map(it) },
            item.casts.map { castMapper.map(it) },
            item.crews.map { crewMapper.map(it) },
            item.translations.map { translationMapper.map(it) }
        )
}
