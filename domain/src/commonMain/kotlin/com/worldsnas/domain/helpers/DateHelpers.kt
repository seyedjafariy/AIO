package com.worldsnas.domain.helpers

import io.islandtime.Instant
import io.islandtime.toDate

// pattern for "yyyy-MM-dd"

fun String.toDate(): Instant =
    Instant.fromUnixEpochMillisecond(toDate().daysSinceUnixEpoch.inMilliseconds.value)

fun Instant.toStringDate(): String =
    toString().let {
        it.substring(0, it.indexOf('T'))
    }
