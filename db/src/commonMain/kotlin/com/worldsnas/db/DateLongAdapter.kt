package com.worldsnas.db

import com.squareup.sqldelight.ColumnAdapter

class DateLongAdapter : ColumnAdapter<Date, Long> {
    override fun decode(databaseValue: Long): Date =
        Date(databaseValue)

    override fun encode(value: Date): Long =
        value.getTime()
}