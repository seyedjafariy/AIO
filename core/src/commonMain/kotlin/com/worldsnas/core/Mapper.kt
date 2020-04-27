package com.worldsnas.core

interface Mapper<T, R>{
    fun map(item : T) : R
}