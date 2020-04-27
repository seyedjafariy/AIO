package com.worldsnas.domain.helpers

const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/"

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

    fun getCached(displayWidth: Int): String {
        if (cachedSize.isNotBlank())
            return cachedSize

        if (displayWidth == 0) {
            cachedSize = sizes.getValue(0)
        }

        cachedSize = getSize(displayWidth)

        return cachedSize
    }

    fun getSize(displayWidth: Int) : String{
        var distance = displayWidth
        var index = 0
        for ((key, _) in sizes) {
            val cdistance = Math.abs(key - displayWidth)
            if (cdistance < distance) {
                index = key
                distance = cdistance
            }
        }
        return sizes.getValue(index)
    }
}

infix fun String.posterFullUrl(width : Int) =
    ImageInfo(ImageType.Poster, this).getFullImageUrlForSize(width)

infix fun String.coverFullUrl(width : Int) =
    ImageInfo(ImageType.Backdrop, this).getFullImageUrlForSize(width)

infix fun String.profileFullUrl(width : Int) =
    ImageInfo(ImageType.Profile, this).getFullImageUrlForSize(width)

infix fun String.logoFullUrl(width : Int) =
    ImageInfo(ImageType.Logo, this).getFullImageUrlForSize(width)

infix fun ImageInfo.getFullImageUrlForSize(width : Int) =
    BASE_IMAGE_URL + type.getSize(width) + url