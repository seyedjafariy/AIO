package com.worldsnas.domain.di

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
import com.worldsnas.domain.mappers.CastEntityRepoMapper
import com.worldsnas.domain.mappers.CastServerEntityMapper
import com.worldsnas.domain.mappers.CompanyEntityRepoMapper
import com.worldsnas.domain.mappers.CompanyServerEntityMapper
import com.worldsnas.domain.mappers.CountryEntityRepoMapper
import com.worldsnas.domain.mappers.CountryServerEntityMapper
import com.worldsnas.domain.mappers.CrewEntityRepoMapper
import com.worldsnas.domain.mappers.CrewServerEntityMapper
import com.worldsnas.domain.mappers.GenreEntityRepoMapper
import com.worldsnas.domain.mappers.GenreServerEntityMapper
import com.worldsnas.domain.mappers.ImageEntityRepoMapper
import com.worldsnas.domain.mappers.ImageServerEntityMapper
import com.worldsnas.domain.mappers.LanguageEntityRepoMapper
import com.worldsnas.domain.mappers.LanguageServerEntityMapper
import com.worldsnas.domain.mappers.MovieEntityRepoMapper
import com.worldsnas.domain.mappers.MovieServerEntityMapper
import com.worldsnas.domain.mappers.ReviewEntityRepoMapper
import com.worldsnas.domain.mappers.ReviewServerEntityMapper
import com.worldsnas.domain.mappers.TranslationEntityRepoMapper
import com.worldsnas.domain.mappers.TranslationServerEntityMapper
import com.worldsnas.domain.mappers.VideoEntityRepoMapper
import com.worldsnas.domain.mappers.VideoServerEntityMapper
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
import com.worldsnas.domain.model.servermodels.ImageServerModel
import com.worldsnas.domain.model.servermodels.LanguageServerModel
import com.worldsnas.domain.model.servermodels.MovieServerModel
import com.worldsnas.domain.model.servermodels.ReviewServerModel
import com.worldsnas.domain.model.servermodels.TranslationServerModel
import com.worldsnas.domain.model.servermodels.VideoServerModel
import com.worldsnas.panther.Mapper
import dagger.Binds
import dagger.Module

@Module(includes = [ServerMapperModule::class])
abstract class MappersModule {

    @Binds
    abstract fun bindCastEntityRepoMapper(mapper: CastEntityRepoMapper):
        Mapper<CastEntity, CastRepoModel>

    @Binds
    abstract fun bindCastServerEntityMapper(mapper: CastServerEntityMapper):
        Mapper<CastServerModel, CastEntity>

    @Binds
    abstract fun bindCompanyEntityRepoMapper(mapper: CompanyEntityRepoMapper):
        Mapper<CompanyEntity, CompanyRepoModel>

    @Binds
    abstract fun bindCompanyServerEntityMapper(mapper: CompanyServerEntityMapper):
        Mapper<CompanyServerModel, CompanyEntity>

    @Binds
    abstract fun bindCountryEntityRepoMapper(mapper: CountryEntityRepoMapper):
        Mapper<CountryEntity, CountryRepoModel>

    @Binds
    abstract fun bindCountryServerEntityMapper(mapper: CountryServerEntityMapper):
        Mapper<CountryServerModel, CountryEntity>

    @Binds
    abstract fun bindCrewEntityRepoMapper(mapper: CrewEntityRepoMapper):
        Mapper<CrewEntity, CrewRepoModel>

    @Binds
    abstract fun bindCrewServerEntityMapper(mapper: CrewServerEntityMapper):
        Mapper<CrewServerModel, CrewEntity>

    @Binds
    abstract fun bindGenreEntityRepoMapper(mapper: GenreEntityRepoMapper):
        Mapper<GenreEntity, GenreRepoModel>

    @Binds
    abstract fun bindGenreServerEntityMapper(mapper: GenreServerEntityMapper):
        Mapper<GenreServerModel, GenreEntity>

    @Binds
    abstract fun bindImageEntityRepoMapper(mapper: ImageEntityRepoMapper):
        Mapper<ImageEntity, ImageRepoModel>

    @Binds
    abstract fun bindImageServerEntityMapper(mapper: ImageServerEntityMapper):
        Mapper<ImageServerModel, ImageEntity>

    @Binds
    abstract fun bindLanguageEntityRepoMapper(mapper: LanguageEntityRepoMapper):
        Mapper<LanguageEntity, LanguageRepoModel>

    @Binds
    abstract fun bindLanguageServerEntityMapper(mapper: LanguageServerEntityMapper):
        Mapper<LanguageServerModel, LanguageEntity>

    @Binds
    abstract fun bindMovieEntityRepoMapper(mapper: MovieEntityRepoMapper):
        Mapper<MovieEntity, MovieRepoModel>

    @Binds
    abstract fun bindMovieServerEntityMapper(mapper: MovieServerEntityMapper):
        Mapper<MovieServerModel, MovieEntity>

    @Binds
    abstract fun bindReviewEntityRepoMapper(mapper: ReviewEntityRepoMapper):
        Mapper<ReviewEntity, ReviewRepoModel>

    @Binds
    abstract fun bindReviewServerEntityMapper(mapper: ReviewServerEntityMapper):
        Mapper<ReviewServerModel, ReviewEntity>

    @Binds
    abstract fun bindTranslationEntityRepoMapper(mapper: TranslationEntityRepoMapper):
        Mapper<TranslationEntity, TranslationRepoModel>

    @Binds
    abstract fun bindTranslationServerEntityMapper(mapper: TranslationServerEntityMapper):
        Mapper<TranslationServerModel, TranslationEntity>

    @Binds
    abstract fun bindVideoEntityRepoMapper(mapper: VideoEntityRepoMapper):
        Mapper<VideoEntity, VideoRepoModel>

    @Binds
    abstract fun bindVideoServerEntityMapper(mapper: VideoServerEntityMapper):
        Mapper<VideoServerModel, VideoEntity>

}