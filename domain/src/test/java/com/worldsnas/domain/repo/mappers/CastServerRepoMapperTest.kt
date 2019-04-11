package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.CastServerModel
import com.worldsnas.kotlintesthelpers.randomBoolean
import com.worldsnas.kotlintesthelpers.randomDouble
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class CastServerRepoMapperTest {

    private val mapper = CastServerRepoMapper()

    @Test
    fun `mapper returns correct repo model`() {
        val server = CastServerModel(
            randomInt(),
            randomString(),
            randomString(),
            randomInt(),
            randomLong(),
            randomString(),
            randomInt(),
            randomString(),
            randomString(),
            randomBoolean(),
            randomString(),
            randomInt(),
            randomBoolean(),
            randomDouble(),
            intArrayOf(
                randomInt(),
                randomInt(),
                randomInt()
            ),
            randomString(),
            randomString(),
            randomString(),
            randomString(),
            randomDouble(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}