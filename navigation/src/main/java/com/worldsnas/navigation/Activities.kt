package com.worldsnas.navigation

import android.os.Bundle

sealed class Activities(val name: String, val extras: Bundle? = null) {

    object Main : Activities("com.worldsnas.aio.MainActivity")
}