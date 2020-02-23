package com.worldsnas.core

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class FlowExtTest{

    @Test
    fun `lists all items`() = runBlockingTest {
        val value = flowOf(1,2,3)
            .toListFlow()
            .first()

        assertThat(value)
            .hasSize(3)
            .isEqualTo(listOf(1,2,3))
    }

    @Test
    fun `emits empty list`() = runBlockingTest {
        val value = flowOf<Int>()
            .toListFlow()
            .first()

        assertThat(value)
            .hasSize(0)
            .isEqualTo(listOf<Int>())
    }

}