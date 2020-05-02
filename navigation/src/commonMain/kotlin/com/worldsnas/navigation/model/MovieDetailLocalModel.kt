package com.worldsnas.navigation.model

import com.worldsnas.navigation.SinkSerializer
import okio.BufferedSink
import okio.BufferedSource

data class MovieDetailLocalModel(
    val movieID: Long,
    val poster: String,
    val cover: String,
    val title: String,
    val description: String,
    val releasedDate: String,
    val posterTransName: String = "",
    val releaseTransName: String = "",
    val titleTransName: String = "",
    val coverTransName: String = ""
) {
    companion object Sinker : SinkSerializer<MovieDetailLocalModel> {
        const val EXTRA_MOVIE = "extra_movie"

        override fun BufferedSource.readFromSink(): MovieDetailLocalModel =
            MovieDetailLocalModel(
                readLong(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8(),
                readUtf8()
            )

        override fun BufferedSink.writeToSink(obj: MovieDetailLocalModel) {
            writeLong(obj.movieID)
            writeUtf8(obj.poster)
            writeUtf8(obj.cover)
            writeUtf8(obj.title)
            writeUtf8(obj.description)
            writeUtf8(obj.releasedDate)
            writeUtf8(obj.posterTransName)
            writeUtf8(obj.releaseTransName)
            writeUtf8(obj.titleTransName)
            writeUtf8(obj.coverTransName)
        }
    }
}