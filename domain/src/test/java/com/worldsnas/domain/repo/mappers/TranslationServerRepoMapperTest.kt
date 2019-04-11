package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.TranslationDataServerModel
import com.worldsnas.domain.servermodels.TranslationServerModel
import com.worldsnas.kotlintesthelpers.randomString
import com.worldsnas.kotlintesthelpers.softAssert
import org.junit.Test

class TranslationServerRepoMapperTest {

    private val mapper = TranslationServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = TranslationServerModel(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            TranslationDataServerModel(
                randomString(),
                randomString(),
                randomString()
            )
        )

        val repo = mapper.map(server)
        softAssert {
            assertThat(repo.englishName).isEqualTo(server.englishName)
            assertThat(repo.iso_3166_1).isEqualTo(server.iso_3166_1)
            assertThat(repo.iso_639_1).isEqualTo(server.iso_639_1)
            assertThat(repo.name).isEqualTo(server.name)
            assertThat(repo.homePage).isEqualTo(server.data!!.homePage)
            assertThat(repo.overview).isEqualTo(server.data!!.overview)
            assertThat(repo.title).isEqualTo(server.data!!.title)
        }
    }

    @Test
    fun `mapper returns correct model with null data`() {
        val server = TranslationServerModel(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            null
        )

        val repo = mapper.map(server)
        softAssert {
            assertThat(repo.englishName).isEqualTo(server.englishName)
            assertThat(repo.iso_3166_1).isEqualTo(server.iso_3166_1)
            assertThat(repo.iso_639_1).isEqualTo(server.iso_639_1)
            assertThat(repo.name).isEqualTo(server.name)
            assertThat(repo.homePage).isEqualTo("")
            assertThat(repo.overview).isEqualTo("")
            assertThat(repo.title).isEqualTo("")
        }
    }
}