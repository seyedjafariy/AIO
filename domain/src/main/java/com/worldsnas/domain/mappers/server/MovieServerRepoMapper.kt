package com.worldsnas.domain.mappers.server

import com.worldsnas.domain.repomodel.CastRepoModel
import com.worldsnas.domain.repomodel.CompanyRepoModel
import com.worldsnas.domain.repomodel.CountryRepoModel
import com.worldsnas.domain.repomodel.CrewRepoModel
import com.worldsnas.domain.repomodel.GenreRepoModel
import com.worldsnas.domain.repomodel.ImageRepoModel
import com.worldsnas.domain.repomodel.LanguageRepoModel
import com.worldsnas.domain.repomodel.MovieRepoModel
import com.worldsnas.domain.repomodel.ReviewRepoModel
import com.worldsnas.domain.repomodel.TranslationRepoModel
import com.worldsnas.domain.repomodel.VideoRepoModel
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
            item.externalIds?.twitterId ?: "",
            item.spokenLanguages.map { languageMapper.map(it) },
            item.productionCompanies.map { companyMapper.map(it) },
            item.productionCountries.map { countryMapper.map(it) },
            item.genres.map { genreMapper.map(it) },
            item.videos?.list?.map { videoMapper.map(it) } ?: emptyList(),
            item.images?.backdrops?.map { imageMapper.map(it) } ?: emptyList(),
            item.images?.posters?.map { imageMapper.map(it) } ?: emptyList(),
            item.reviews?.list?.map { reviewMapper.map(it) } ?: emptyList(),
            item.similar?.list?.map { map(it) } ?: emptyList(),
            item.recommendations?.list?.map { map(it) } ?: emptyList(),
            item.credits?.casts?.map { castMapper.map(it) } ?: emptyList(),
            item.credits?.crews?.map { crewMapper.map(it) } ?: emptyList(),
            item.translations?.translations?.map { translationMapper.map(it) } ?: emptyList()
        )
}