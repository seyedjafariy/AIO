package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.ReviewServerModel
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class ReviewServerRepoMapperTest{

    private val mapper = ReviewServerRepoMapper()

    @Test
    fun `mapper returns correct model`() {
        val server = ReviewServerModel(
            randomString(),
            randomString(),
            randomString(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }

}