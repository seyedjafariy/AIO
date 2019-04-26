package com.worldsnas.domain.di

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
import dagger.Binds
import dagger.Module

@Module
abstract class ServerMapperModule {

    @Binds
    abstract fun bindGenreMapper(mapper : GenreServerRepoMapper) :
        Mapper<GenreServerModel, GenreRepoModel>

    @Binds
    abstract fun bindCompanyMapper(mapper : CompanyServerRepoMapper) :
        Mapper<CompanyServerModel, CompanyRepoModel>

    @Binds
    abstract fun bindCountryServerRepoMapper(mapper : CountryServerRepoMapper) :
        Mapper<CountryServerModel, CountryRepoModel>

    @Binds
    abstract fun bindLanguageServerRepoMapper(mapper : LanguageServerRepoMapper) :
        Mapper<LanguageServerModel, LanguageRepoModel>

    @Binds
    abstract fun bindVideoServerRepoMapper(mapper : VideoServerRepoMapper) :
        Mapper<VideoServerModel, VideoRepoModel>

    @Binds
    abstract fun bindImageServerRepoMapper(mapper : ImageServerRepoMapper) :
        Mapper<ImageServerModel, ImageRepoModel>

    @Binds
    abstract fun bindReviewServerRepoMapper(mapper : ReviewServerRepoMapper) :
        Mapper<ReviewServerModel, ReviewRepoModel>

    @Binds
    abstract fun bindCastServerRepoMapper(mapper : CastServerRepoMapper) :
        Mapper<CastServerModel, CastRepoModel>

    @Binds
    abstract fun bindCrewServerRepoMapper(mapper : CrewServerRepoMapper) :
        Mapper<CrewServerModel, CrewRepoModel>

    @Binds
    abstract fun bindTranslationServerRepoMapper(mapper : TranslationServerRepoMapper) :
        Mapper<TranslationServerModel, TranslationRepoModel>

    @Binds
    abstract fun bindMovieServerRepoMapper(mapper : MovieServerRepoMapper) :
        Mapper<MovieServerModel, MovieRepoModel>

}