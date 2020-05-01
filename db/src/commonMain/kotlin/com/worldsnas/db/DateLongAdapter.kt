package com.worldsnas.db

import com.squareup.sqldelight.ColumnAdapter
import io.islandtime.Instant
import io.islandtime.measures.LongMilliseconds

class DateLongAdapter : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant =
        Instant(LongMilliseconds(databaseValue))

    override fun encode(value: Instant): Long =
        value.unixEpochMillisecond
}