package com.worldsnas.domain.repo.mappers

import com.worldsnas.domain.servermodels.GenreServerModel
import com.worldsnas.kotlintesthelpers.randomLong
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions
import org.junit.Test

class GenreServerRepoMapperTest{

    private val mapper = GenreServerRepoMapper()

    @Test
    fun `mapper returns correct model`(){
        val server = GenreServerModel(
            randomLong(),
            randomString()
        )

        Assertions.assertThat(mapper.map(server))
            .isEqualToComparingFieldByField(server)
    }
}