package com.worldsnas.db

import com.squareup.sqldelight.ColumnAdapter
import java.util.*

class DateIntegerAdapter : ColumnAdapter<Date, Long> {
    override fun decode(databaseValue: Long): Date =
        Date(databaseValue)

    override fun encode(value: Date): Long =
        value.time

}