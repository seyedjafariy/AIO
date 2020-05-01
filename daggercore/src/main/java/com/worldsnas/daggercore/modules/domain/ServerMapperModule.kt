package com.worldsnas.daggercore.modules.domain

import com.worldsnas.domain.mappers.server.CastServerRepoMapper
import com.worldsnas.domain.mappers.server.CompanyServerRepoMapper
import com.worldsnas.domain.mappers.server.CountryServerRepoMapper
import com.worldsnas.domain.mappers.server.CrewServerRepoMapper
import com.worldsnas.domain.mappers.server.GenreServerRepoMapper
import com.worldsnas.domain.mappers.server.ImageServerRepoMapper
import com.worldsnas.domain.mappers.server.LanguageServerRepoMapper
import com.worldsnas.domain.mappers.server.MovieServerRepoMapper
import com.worldsnas.domain.mappers.server.ReviewServerRepoMapper
import com.worldsnas.domain.mappers.server.TranslationServerRepoMapper
import com.worldsnas.domain.mappers.server.VideoServerRepoMapper
import com.worldsnas.domain.model.ImageServerTypeHolder
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
import com.worldsnas.domain.model.servermodels.CastServerModel
import com.worldsnas.domain.model.servermodels.CompanyServerModel
import com.worldsnas.domain.model.servermodels.CountryServerModel
import com.worldsnas.domain.model.servermodels.CrewServerModel
import com.worldsnas.domain.model.servermodels.GenreServerModel
import com.worldsnas.domain.model.servermodels.LanguageServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ReviewServerModel
import com.worldsnas.domain.model.servermodels.TranslationServerModel
import com.worldsnas.domain.model.servermodels.VideoServerModel
import com.worldsnas.core.Mapper
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
object ServerMapperModule {

    @Provides
    @JvmStatic
    fun bindGenreMapper(): Mapper<GenreServerModel, GenreRepoModel> = GenreServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindCompanyMapper(): Mapper<CompanyServerModel, CompanyRepoModel> =
        CompanyServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindCountryServerRepoMapper(): Mapper<CountryServerModel, CountryRepoModel> =
        CountryServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindLanguageServerRepoMapper(): Mapper<LanguageServerModel, LanguageRepoModel> =
        LanguageServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindVideoServerRepoMapper(): Mapper<VideoServerModel, VideoRepoModel> =
        VideoServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindImageServerRepoMapper(): Mapper<ImageServerTypeHolder, ImageRepoModel> =
        ImageServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindReviewServerRepoMapper(): Mapper<ReviewServerModel, ReviewRepoModel> =
        ReviewServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindCastServerRepoMapper(): Mapper<CastServerModel, CastRepoModel> = CastServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindCrewServerRepoMapper(): Mapper<CrewServerModel, CrewRepoModel> = CrewServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindTranslationServerRepoMapper(): Mapper<TranslationServerModel, TranslationRepoModel> =
        TranslationServerRepoMapper()

    @Provides
    @JvmStatic
    fun bindMovieServerRepoMapper(
        genreMapper: Mapper<GenreServerModel, GenreRepoModel>,
        companyMapper: Mapper<CompanyServerModel, CompanyRepoModel>,
        countryMapper: Mapper<CountryServerModel, CountryRepoModel>,
        languageMapper: Mapper<LanguageServerModel, LanguageRepoModel>,
        videoMapper: Mapper<VideoServerModel, VideoRepoModel>,
        imageMapper: Mapper<ImageServerTypeHolder, ImageRepoModel>,
        reviewMapper: Mapper<ReviewServerModel, ReviewRepoModel>,
        castMapper: Mapper<CastServerModel, CastRepoModel>,
        crewMapper: Mapper<CrewServerModel, CrewRepoModel>,
        translationMapper: Mapper<TranslationServerModel, TranslationRepoModel>
    ): Mapper<MovieServerModel, MovieRepoModel> = MovieServerRepoMapper(
        genreMapper,
        companyMapper,
        countryMapper,
        languageMapper,
        videoMapper,
        imageMapper,
        reviewMapper,
        castMapper,
        crewMapper,
        translationMapper
    )
}