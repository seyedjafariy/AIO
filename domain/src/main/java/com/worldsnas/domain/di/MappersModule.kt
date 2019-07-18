package com.worldsnas.domain.di

import com.worldsnas.domain.entity.*
import com.worldsnas.domain.mappers.*
import com.worldsnas.domain.model.repomodel.*
import com.worldsnas.domain.model.servermodels.*
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

    @Binds
    abstract fun bindPersonServerRepoMapper(mapper: PersonServerRepoMapper):
            Mapper<PersonServerModel, PersonRepoModel>

}