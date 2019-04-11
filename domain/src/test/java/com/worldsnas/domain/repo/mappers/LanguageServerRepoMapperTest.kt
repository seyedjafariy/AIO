package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.LanguageServerModel
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class LanguageServerRepoMapperTest {

    private val mapper = LanguageServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = LanguageServerModel(
            randomString(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}