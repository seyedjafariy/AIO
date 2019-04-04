package com.worldsnas.home

import com.worldsnas.navigation.Navigation
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationTest {

    @Test
    fun `navigation returns correct object`(){
        assertTrue(Navigation.createController(Navigation.HOME) is HomeView)
    }
}