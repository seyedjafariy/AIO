package com.worldsnas.domain.helpers

import com.worldsnas.base.DisplaySize
import com.worldsnas.kotlintesthelpers.randomInt
import com.worldsnas.kotlintesthelpers.randomString
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test

class TMDBImageUrlFactoryTest {

    @Test
    fun `returns correct size for exact number`() {
        val url = randomString()
        val displaySize = com.worldsnas.base.DisplaySize(randomInt(), 342)
        val factory = TMDBImageUrlFactory(displaySize)

        assertThat(
            factory.create(
                ImageInfo(
                    ImageType.Poster,
                    url
                )
            )
        ).isEqualToIgnoringCase(BASE_IMAGE_URL + "w342" + url)
    }

    @Test
    fun `returns rounded down size`() {
        val url = randomString()
        val displaySize = com.worldsnas.base.DisplaySize(randomInt(), 1080)
        val factory = TMDBImageUrlFactory(displaySize)

        assertThat(
            factory.create(
                ImageInfo(
                    ImageType.Poster,
                    url
                )
            )
        ).isEqualToIgnoringCase(BASE_IMAGE_URL + "w780" + url)
    }

    @Test
    fun `providing 0 size will give us original image size`(){
        val url = randomString()
        val displaySize = com.worldsnas.base.DisplaySize(randomInt(), 0)
        val factory = TMDBImageUrlFactory(displaySize)

        assertThat(
            factory.create(
                ImageInfo(
                    ImageType.Poster,
                    url
                )
            )
        ).isEqualToIgnoringCase(BASE_IMAGE_URL + "original" + url)
    }

    @After
    fun tearDown(){
        resetAllImageTypes()
    }

    private fun resetAllImageTypes(){
        ImageType.Backdrop.cachedSize = ""
        ImageType.Poster.cachedSize = ""
        ImageType.Logo.cachedSize = ""
        ImageType.Profile.cachedSize = ""
    }
}