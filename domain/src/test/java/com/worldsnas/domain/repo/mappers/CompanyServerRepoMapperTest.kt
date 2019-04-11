package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.CompanyServerModel
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CompanyServerRepoMapperTest {

    private val mapper = CompanyServerRepoMapper()

    @Test
    fun `mapper returns correct repo model`() {
        val server = CompanyServerModel(
            randomLong(),
            randomString(),
            randomString(),
            randomString()
        )

        assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}