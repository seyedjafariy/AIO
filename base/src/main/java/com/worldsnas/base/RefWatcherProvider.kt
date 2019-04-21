package com.worldsnas.base

import com.squareup.leakcanary.RefWatcher

interface RefWatcherProvider {

    fun getRefWatcher() : RefWatcher
}