package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.repo.model.CastRepoModel
import com.worldsnas.domain.repo.model.CompanyRepoModel
import com.worldsnas.domain.repo.model.CountryRepoModel
import com.worldsnas.domain.repo.model.CrewRepoModel
import com.worldsnas.domain.repo.model.GenreRepoModel
import com.worldsnas.domain.repo.model.ImageRepoModel
import com.worldsnas.domain.repo.model.LanguageRepoModel
import com.worldsnas.domain.repo.model.ReviewRepoModel
import com.worldsnas.domain.repo.model.TranslationRepoModel
import com.worldsnas.domain.repo.model.VideoRepoModel
import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.domain.servermodels.CountryServerModel
import com.worldsnas.domain.servermodels.CreditsServerModel
import com.worldsnas.domain.servermodels.CrewServerModel
import com.worldsnas.domain.servermodels.ExternalIdsSeverModel
import com.worldsnas.domain.servermodels.FullImageServerModel
import com.worldsnas.domain.servermodels.GenreServerModel
import com.worldsnas.domain.servermodels.ImageServerModel
import com.worldsnas.domain.servermodels.LanguageServerModel
import com.worldsnas.domain.servermodels.MovieServerModel
import com.worldsnas.domain.servermodels.ResultsServerModel
import com.worldsnas.domain.servermodels.ReviewServerModel
import com.worldsnas.domain.servermodels.TranslationListServerModel
import com.worldsnas.domain.servermodels.TranslationServerModel
import com.worldsnas.domain.servermodels.VideoServerModel
import com.worldsnas.kotlintesthelpers.randomBoolean
import com.worldsnas.kotlintesthelpers.randomDouble
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import com.worldsnas.kotlintesthelpers.softAssert
import com.worldsnas.panther.Mapper
import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.Before
import org.junit.Test

class MovieServerRepoMapperTest {

    private val genreMapper: Mapper<GenreServerModel, GenreRepoModel> = mockk(relaxed = true)
    private val companyMapper: Mapper<CompanyServerModel, CompanyRepoModel> = mockk(relaxed = true)
    private val countryMapper: Mapper<CountryServerModel, CountryRepoModel> = mockk(relaxed = true)
    private val languageMapper: Mapper<LanguageServerModel, LanguageRepoModel> = mockk(relaxed = true)
    private val videoMapper: Mapper<VideoServerModel, VideoRepoModel> = mockk(relaxed = true)
    private val imageMapper: Mapper<ImageServerModel, ImageRepoModel> = mockk(relaxed = true)
    private val reviewMapper: Mapper<ReviewServerModel, ReviewRepoModel> = mockk(relaxed = true)
    private val castMapper: Mapper<CastServerModel, CastRepoModel> = mockk(relaxed = true)
    private val crewMapper: Mapper<CrewServerModel, CrewRepoModel> = mockk(relaxed = true)
    private val translationMapper: Mapper<TranslationServerModel, TranslationRepoModel> = mockk(relaxed = true)

    val mapper = MovieServerRepoMapper(
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

    @Before
    fun setup(){
        clearMocks(
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

    @Test
    fun `mapper returns correct repo model`() {
        val server = MovieServerModel(
            randomLong(),
            randomBoolean(),
            randomString(),
            randomLong(),
            emptyList(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomDouble(),
            randomString(),
            emptyList(),
            emptyList(),
            randomString(),
            randomLong(),
            randomInt(),
            emptyList(),
            randomString(),
            randomString(),
            randomString(),
            randomBoolean(),
            randomDouble(),
            randomInt(),
            ResultsServerModel(),
            FullImageServerModel(),
            ResultsServerModel(),
            ResultsServerModel(),
            ResultsServerModel(),
            CreditsServerModel(),
            TranslationListServerModel(),
            ExternalIdsSeverModel(
                randomString(),
                randomString(),
                randomString(),
                randomString()))

        val actual = mapper.map(server)

        softAssert {
            assertThat(actual.id).isEqualTo(server.id)
            assertThat(actual.adult).isEqualTo(server.adult)
            assertThat(actual.backdropPath).isEqualTo(server.backdropPath)
            assertThat(actual.budget).isEqualTo(server.budget)
            assertThat(actual.homePage).isEqualTo(server.homePage)
            assertThat(actual.imdbID).isEqualTo(server.imdbID)
            assertThat(actual.originalLanguage).isEqualTo(server.originalLanguage)
            assertThat(actual.originalTitle).isEqualTo(server.originalTitle)
            assertThat(actual.overview).isEqualTo(server.overview)
            assertThat(actual.popularity).isEqualTo(server.popularity)
            assertThat(actual.posterPath).isEqualTo(server.posterPath)
            assertThat(actual.releaseDate).isEqualTo(server.releaseDate)
            assertThat(actual.revenue).isEqualTo(server.revenue)
            assertThat(actual.runtime).isEqualTo(server.runtime)
            assertThat(actual.runtime).isEqualTo(server.runtime)
            assertThat(actual.status).isEqualTo(server.status)
            assertThat(actual.tagLine).isEqualTo(server.tagLine)
            assertThat(actual.video).isEqualTo(server.video)
            assertThat(actual.voteAverage).isEqualTo(server.voteAverage)
            assertThat(actual.voteCount).isEqualTo(server.voteCount)
            assertThat(actual.facebookId).isEqualTo(server.externalIds.facebookId)
            assertThat(actual.instagramId).isEqualTo(server.externalIds.instagramId)
            assertThat(actual.twitterId).isEqualTo(server.externalIds.twitterId)
        }
    }
}