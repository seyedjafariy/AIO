package com.worldsnas.kotlintesthelpers.helpers

import java.io.File

fun getJson(path : String) : String {
    // Load the JSON response
    val uri = ClassLoader.getSystemClassLoader().getResource(path).toURI()
    val file = File(uri)
    return String(file.readBytes())
}