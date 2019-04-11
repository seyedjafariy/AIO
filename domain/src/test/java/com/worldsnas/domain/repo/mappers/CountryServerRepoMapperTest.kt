package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.CountryServerModel
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class CountryServerRepoMapperTest{

    private val mapper = CountryServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = CountryServerModel(
            randomString(),
            randomString()
        )

        assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}