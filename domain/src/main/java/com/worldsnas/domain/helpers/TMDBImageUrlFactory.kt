package com.worldsnas.domain.helpers

import com.worldsnas.core.DisplaySize
import com.worldsnas.panther.Factory
import dagger.Reusable
import javax.inject.Inject

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

@Reusable
class TMDBImageUrlFactory @Inject constructor(
    private val displaySize: DisplaySize
) : Factory<ImageInfo, String> {

    override fun create(item: ImageInfo): String {
        val size = item.type.getSize(displaySize.width)
        return createURL(item.url, size)
    }

    private fun createURL(imageURl: String, size: String) =
        BASE_IMAGE_URL + size + imageURl
}

data class ImageInfo(
    val type: ImageType,
    val url: String
)

sealed class ImageType(
    private val sizes: Map<Int, String>,
    var cachedSize: String = ""
) {
    object Poster : ImageType(
        mapOf(
            0 to "original",
            92 to "w92",
            154 to "w154",
            185 to "w185",
            342 to "w342",
            500 to "w500",
            780 to "w780"
        )
    )

    object Logo : ImageType(
        mapOf(
            0 to "original",
            45 to "w45",
            92 to "w92",
            154 to "w154",
            185 to "w185",
            300 to "w300",
            500 to "w500"
        )
    )

    object Backdrop : ImageType(
        mapOf(
            0 to "original",
            300 to "w300",
            780 to "w780",
            1280 to "w1280"
        )
    )

    object Profile : ImageType(
        mapOf(
            0 to "original",
            45 to "w45",
            185 to "w185"
        )
    )

    fun getSize(displayWidth: Int): String {
        if (cachedSize.isNotBlank())
            return cachedSize

        if (displayWidth == 0) {
            cachedSize = sizes.getValue(0)
        }

        var distance = displayWidth
        var index = 0
        for ((key, _) in sizes) {
            val cdistance = Math.abs(key - displayWidth)
            if (cdistance < distance) {
                index = key
                distance = cdistance
            }
        }
        cachedSize = sizes.getValue(index)

        return cachedSize
    }
}