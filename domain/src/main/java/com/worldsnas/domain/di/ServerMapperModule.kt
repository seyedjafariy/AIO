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
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module
abstract class ServerMapperModule {

    @Binds
    abstract fun bindGenreMapper(mapper: GenreServerRepoMapper):
        Mapper<GenreServerModel, GenreRepoModel>

    @Binds
    abstract fun bindCompanyMapper(mapper: CompanyServerRepoMapper):
        Mapper<CompanyServerModel, CompanyRepoModel>

    @Binds
    abstract fun bindCountryServerRepoMapper(mapper: CountryServerRepoMapper):
        Mapper<CountryServerModel, CountryRepoModel>

    @Binds
    abstract fun bindLanguageServerRepoMapper(mapper: LanguageServerRepoMapper):
        Mapper<LanguageServerModel, LanguageRepoModel>

    @Binds
    abstract fun bindVideoServerRepoMapper(mapper: VideoServerRepoMapper):
        Mapper<VideoServerModel, VideoRepoModel>

    @Binds
    abstract fun bindImageServerRepoMapper(mapper: ImageServerRepoMapper):
        Mapper<ImageServerTypeHolder, ImageRepoModel>

    @Binds
    abstract fun bindReviewServerRepoMapper(mapper: ReviewServerRepoMapper):
        Mapper<ReviewServerModel, ReviewRepoModel>

    @Binds
    abstract fun bindCastServerRepoMapper(mapper: CastServerRepoMapper):
        Mapper<CastServerModel, CastRepoModel>

    @Binds
    abstract fun bindCrewServerRepoMapper(mapper: CrewServerRepoMapper):
        Mapper<CrewServerModel, CrewRepoModel>

    @Binds
    abstract fun bindTranslationServerRepoMapper(mapper: TranslationServerRepoMapper):
        Mapper<TranslationServerModel, TranslationRepoModel>

    @Binds
    abstract fun bindMovieServerRepoMapper(mapper: MovieServerRepoMapper):
        Mapper<MovieServerModel, MovieRepoModel>
}