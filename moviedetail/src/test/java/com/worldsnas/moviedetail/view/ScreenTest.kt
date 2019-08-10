package com.worldsnas.moviedetail.view

import androidx.test.filters.MediumTest
import com.worldsnas.navigation.Navigation
import com.worldsnas.navigation.Screens
import com.worldsnas.navigation.model.MovieDetailLocalModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ScreenTest {

    @Test
    @MediumTest
    fun movieDetailScreenBoundedCorrectly() {
        assertThat(
            Navigation.createController(
                Screens.MovieDetail(
                    MovieDetailLocalModel(
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                    )
                )
            )
        ).isInstanceOf(MovieDetailView::class.java)
    }

}