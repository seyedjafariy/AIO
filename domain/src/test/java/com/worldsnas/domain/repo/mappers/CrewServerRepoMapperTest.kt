package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.CrewServerModel
import com.worldsnas.kotlintesthelpers.randomBoolean
import com.worldsnas.kotlintesthelpers.randomDouble
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class CrewServerRepoMapperTest {

    private val mapper = CrewServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = CrewServerModel(
            randomString(),
            randomString(),
            randomInt(),
            randomLong(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            intArrayOf(
                randomInt(),
                randomInt(),
                randomInt()
            ),
            randomBoolean(),
            randomString(),
            randomDouble(),
            randomDouble(),
            randomInt(),
            randomString(),
            randomBoolean(),
            randomString(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}