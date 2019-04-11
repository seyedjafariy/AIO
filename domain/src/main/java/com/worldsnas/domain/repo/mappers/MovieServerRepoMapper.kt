package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CastRepoModel
import com.worldsnas.domain.repo.model.CompanyRepoModel
import com.worldsnas.domain.repo.model.CountryRepoModel
import com.worldsnas.domain.repo.model.CrewRepoModel
import com.worldsnas.domain.repo.model.GenreRepoModel
import com.worldsnas.domain.repo.model.ImageRepoModel
import com.worldsnas.domain.repo.model.LanguageRepoModel
import com.worldsnas.domain.repo.model.MovieRepoModel
import com.worldsnas.domain.repo.model.ReviewRepoModel
import com.worldsnas.domain.repo.model.TranslationRepoModel
import com.worldsnas.domain.repo.model.VideoRepoModel
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.domain.servermodels.CountryServerModel
import com.worldsnas.domain.servermodels.CrewServerModel
import com.worldsnas.domain.servermodels.GenreServerModel
import com.worldsnas.domain.servermodels.ImageServerModel
import com.worldsnas.domain.servermodels.LanguageServerModel
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ReviewServerModel
import com.worldsnas.domain.servermodels.TranslationServerModel
import com.worldsnas.domain.servermodels.VideoServerModel
import com.worldsnas.panther.Mapper
import javax.inject.Inject

class MovieServerRepoMapper @Inject constructor(
    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel>,
    private val companyMapper: Mapper<CompanyServerModel, CompanyRepoModel>,
    private val countryMapper: Mapper<CountryServerModel, CountryRepoModel>,
    private val languageMapper: Mapper<LanguageServerModel, LanguageRepoModel>,
    private val videoMapper: Mapper<VideoServerModel, VideoRepoModel>,
    private val imageMapper: Mapper<ImageServerModel, ImageRepoModel>,
    private val reviewMapper: Mapper<ReviewServerModel, ReviewRepoModel>,
    private val castMapper: Mapper<CastServerModel, CastRepoModel>,
    private val crewMapper: Mapper<CrewServerModel, CrewRepoModel>,
    private val translationMapper: Mapper<TranslationServerModel, TranslationRepoModel>
) : Mapper<MovieServerModel, MovieRepoModel> {

    override fun map(item: MovieServerModel): MovieRepoModel =
        MovieRepoModel(
            item.id,
            item.adult,
            item.backdropPath,
            item.budget,
            item.genres.map { genreMapper.map(it) },
            item.homePage,
            item.imdbID,
            item.originalLanguage,
            item.originalTitle,
            item.overview,
            item.popularity,
            item.posterPath,
            item.productionCompanies.map { companyMapper.map(it) },
            item.productionCountries.map { countryMapper.map(it) },
            item.releaseDate,
            item.revenue,
            item.runtime,
            item.spokenLanguages.map { languageMapper.map(it) },
            item.status,
            item.tagLine,
            item.title,
            item.video,
            item.voteAverage,
            item.voteCount,
            item.videos.list.map { videoMapper.map(it) },
            item.images.backdrops.map { imageMapper.map(it) },
            item.images.posters.map { imageMapper.map(it) },
            item.reviews.list.map { reviewMapper.map(it) },
            item.similar.list.map { map(it) },
            item.recommendations.list.map { map(it) },
            item.credits.casts.map { castMapper.map(it) },
            item.credits.crews.map { crewMapper.map(it) },
            item.translations.translations.map { translationMapper.map(it) },
            item.externalIds.facebookId,
            item.externalIds.instagramId,
            item.externalIds.twitterId
        )
}