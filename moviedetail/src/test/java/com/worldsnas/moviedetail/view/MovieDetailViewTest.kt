package com.worldsnas.moviedetail.view

import androidx.core.os.bundleOf
import androidx.test.core.app.ActivityScenario
import androidx.test.filters.MediumTest
import androidx.test.runner.AndroidJUnit4
import com.worldsnas.kotlintesthelpers.TestActivity
import com.worldsnas.navigation.model.MovieDetailLocalModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MovieDetailViewTest {

    lateinit var activityScenario: ActivityScenario<TestActivity>

    @Before
    fun setUp() {
//        activityScenario = ActivityScenario.launch(TestActivity::class.java)
    }

    @After
    fun tearDown(){
//        activityScenario.close()
    }

    @Test
    fun `get controller full name`(){

        val local = MovieDetailLocalModel(
            movieID = 1,
            poster = "",
            cover = "",
            title = "",
            description = "",
            releasedDate = ""
        )
        val controller = MovieDetailView(
            bundleOf(
                MovieDetailLocalModel.EXTRA_MOVIE to local
            )
        )
        println(controller.javaClass.name)
//        activityScenario.onActivity { activity ->
//            activity.runOnUiThread {
//
//                activity
//                    .router
//                    .setRoot(
//                        RouterTransaction.with(controller)
//                    )
//            }
//        }
    }
}