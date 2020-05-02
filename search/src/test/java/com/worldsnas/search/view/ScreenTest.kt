package com.worldsnas.search.view

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.facebook.soloader.SoLoader
import com.worldsnas.navigation.ControllerFactory
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.SearchLocalModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScreenTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup() {
            SoLoader.setInTestMode()
        }
    }

    @Test
    @MediumTest
    fun galleryScreenBoundedCorrectly(){
        assertThat(
            ControllerFactory.createController(
                Screens.Search(
                    SearchLocalModel()
                ),
                ApplicationProvider.getApplicationContext()
            )
        ).isInstanceOf(SearchView::class.java)
    }

}