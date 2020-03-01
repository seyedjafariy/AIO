package com.worldsnas.domain.helpers

import java.text.SimpleDateFormat
import java.util.*

private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

fun String.toDate() : Date =
    dateFormatter.parse(this)

fun Date.toStringDate() =
    dateFormatter.format(this)