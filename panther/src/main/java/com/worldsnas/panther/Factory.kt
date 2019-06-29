package com.worldsnas.panther

interface Factory<T, R> {

    fun create(item : T) : R
}