package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.VideoServerModel
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class VideoServerRepoMapperTest{

    private val mapper = VideoServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = VideoServerModel(
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomInt(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}