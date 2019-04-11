package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.ImageServerModel
import com.worldsnas.kotlintesthelpers.randomDouble
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class ImageServerRepoMapperTest {

    private val mapper = ImageServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = ImageServerModel(
            randomDouble(),
            randomString(),
            randomInt(),
            randomString(),
            randomDouble(),
            randomInt(),
            randomInt()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}