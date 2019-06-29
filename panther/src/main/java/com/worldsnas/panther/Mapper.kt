package com.worldsnas.panther

interface Mapper<T, R>{
    fun map(item : T) : R
}