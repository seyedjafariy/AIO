package com.worldsnas.gallery

import androidx.test.filters.MediumTest
import com.worldsnas.navigation.ControllerFactory
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.GalleryImageType
import com.worldsnas.navigation.model.GalleryLocalModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScreenTest {

    @Test
    @MediumTest
    fun galleryScreenBoundedCorrectly(){
        assertThat(
            ControllerFactory.createController(
                Screens.Gallery(
                    GalleryLocalModel(
                        emptyList(),
                        0,
                        GalleryImageType.POSTER
                    )
                )
            )
        ).isInstanceOf(GalleryView::class.java)
    }

}