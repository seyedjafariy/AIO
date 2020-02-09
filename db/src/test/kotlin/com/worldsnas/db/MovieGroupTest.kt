package com.worldsnas.db

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MovieGroupTest {

    val adapter = MovieGroup.Adapter()

    @Test
    fun `encodes to String`() {
        val finalExpected = "1,2"

        val groupList = listOf(MovieGroup.LATEST, MovieGroup.TRENDING)

        val result = adapter.encode(groupList)

        assertThat(result).isEqualTo(finalExpected)
    }

    @Test
    fun `decodes to list`() {
        val finalList = listOf(MovieGroup.TRENDING, MovieGroup.LATEST)

        val groupString = "2,1"

        val result = adapter.decode(groupString)

        assertThat(result).isEqualTo(finalList)
    }

    @Test
    fun `returns empty`() {
        val finalList = listOf<MovieGroup>()

        val groupString = "-1"

        val result = adapter.decode(groupString)

        assertThat(result)
            .usingFieldByFieldElementComparator()
            .isEqualTo(finalList)
    }

    @Test
    fun `encodes to empty string`() {
        val resultString = ""

        val groupList = listOf(MovieGroup.NONE)

        val result = adapter.encode(groupList)

        assertThat(result)
            .isEqualTo(resultString)
    }
}