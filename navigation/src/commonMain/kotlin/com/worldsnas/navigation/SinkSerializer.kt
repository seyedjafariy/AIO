package com.worldsnas.navigation

import okio.Buffer
import okio.BufferedSink
import okio.BufferedSource

class StringSizeSource(private val source: BufferedSource) : BufferedSource by source {
    override fun readUtf8(): String =
        source.readUtf8(readLong())
}

class StringSizeSink(private val sink: BufferedSink) : BufferedSink by sink {
    override fun writeUtf8(string: String): BufferedSink {
        writeLong(string.encodeToByteArray().size.toLong())
        return sink.writeUtf8(string)
    }
}


interface SinkSerializer<T : Any> {

    fun BufferedSource.readFromSink(): T

    fun fromSink(source: BufferedSource): T = StringSizeSource(
        source
    ).readFromSink()

    fun BufferedSink.writeToSink(obj: T)

    fun toSink(obj: T, sink: BufferedSink) {
        StringSizeSink(
            sink
        ).writeToSink(obj)
    }

    fun <T> BufferedSink.writeList(list: List<T>, writer: BufferedSink.(item : T) -> Unit): Unit {
        writeInt(list.size)
        repeat(list.size){index->
            writer(list[index])
        }
    }

    fun <T> BufferedSource.readList(reader: BufferedSource.() -> T): List<T> = buildList {
        repeat(readInt()) {
            add(reader())
        }
    }
}

fun <U, T : SinkSerializer<U>> T.fromByteArray(array: ByteArray): U {
    val buffer = Buffer()

    val model: U
    try {
        buffer.write(array)
        model = fromSink(buffer)
    } finally {
        buffer.close()
    }

    return model
}

infix fun <U : Any, T : SinkSerializer<U>> T.toByteArray(obj: U): ByteArray {
    val buffer = Buffer()

    val bytes: ByteArray
    try {
        toSink(obj, buffer)
        bytes = buffer.readByteArray()
    } finally {
        buffer.close()
    }

    return bytes
}