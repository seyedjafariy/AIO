package com.worldsnas.home

import androidx.test.filters.MediumTest
import com.worldsnas.home.view.HomeView
import com.worldsnas.navigation.Navigation
import com.worldsnas.navigation.Screens
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScreenTest {

    @Test
    @MediumTest
    fun homeScreenBoundedCorrectly(){
        assertThat(Navigation.createController(Screens.Home))
            .isInstanceOf(HomeView::class.java)
    }

}