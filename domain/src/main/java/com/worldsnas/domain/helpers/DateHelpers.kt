package com.worldsnas.domain.helpers

import io.islandtime.Instant
import io.islandtime.locale.Locale
import io.islandtime.parser.*
import io.islandtime.toDate
import io.islandtime.toInstant

// pattern for "yyyy-MM-dd"

fun String.toDate(): Instant =
    Instant.fromUnixEpochMillisecond(toDate().daysSinceUnixEpoch.inMilliseconds.value)

fun Instant.toStringDate(): String =
    toString().let {
        it.substring(0, it.indexOf('T'))
    }
