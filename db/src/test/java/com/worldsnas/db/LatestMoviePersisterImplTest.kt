package com.worldsnas.db

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class LatestMoviePersisterImplTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `returns if flow was empty`() = runBlockingTest {
        val result = flowOf(1,2,3)
            .drop(3)
            .ifEmptyEmit(4)
            .toList()

        assertThat(result).hasSize(1)
            .isEqualTo(listOf(4))
    }

    @Test
    fun `returns null if flow was empty`() = runBlockingTest {
        val result = flowOf(1,2,3)
            .drop(1)
            .ifEmptyEmit(null)
            .toList()

        assertThat(result)
            .hasSize(1)
            .isEqualTo(listOf(1))
    }

    @Test
    fun `does not emit the empty item`() = runBlockingTest {
        val result = flowOf(1,2,3)
            .drop(1)
            .ifEmptyEmit(4)
            .toList()

        assertThat(result)
            .hasSize(2)
            .isEqualTo(listOf(2,3))
    }
}