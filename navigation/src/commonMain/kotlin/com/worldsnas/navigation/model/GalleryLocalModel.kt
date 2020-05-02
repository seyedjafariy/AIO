package com.worldsnas.navigation.model

import com.worldsnas.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource

data class GalleryLocalModel(
    val images: List<String>,
    val startPosition: Int = 0,
    val type: GalleryImageType
) {
    companion object Sinker : SinkSerializer<GalleryLocalModel> {
        const val EXTRA_IMAGES = "EXTRA_IMAGES"


        override fun BufferedSource.readFromSink(): GalleryLocalModel =
            GalleryLocalModel(
                readList { readUtf8() },
                readInt(),
                GalleryImageType.fromRaw(readUtf8())
            )

        override fun BufferedSink.writeToSink(obj: GalleryLocalModel) {
            writeList(obj.images){
                writeUtf8(it)
            }
            writeInt(obj.startPosition)
            writeUtf8(obj.type.raw)
        }
    }
}

enum class GalleryImageType(
    val raw : String
) {
    COVER("cover"),
    POSTER("poster"),
    ;

    companion object {
        fun fromRaw(rawValue : String) =
            values().find {
                it.raw == rawValue
            } ?: throw IllegalArgumentException("rawValue=$rawValue is not supported for gallery Image type")
    }
}