package com.worldsnas.aio

import org.junit.Assert.*
import org.junit.Test

class ExtensionsKtTest{


    @Test
    fun `to number correctly returns numbers only`(){
        val stringContainingNumber = "kjhdah12k3jhasfhioah45ggg7f8jh9kj10hjk11gk12jh13"
        assertEquals(1234578910111213, stringContainingNumber.toNumber().toLong())
    }
}