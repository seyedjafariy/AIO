package com.worldsnas.panther

interface Fetcher<in T : Any?, out R : Any?>{

    fun fetch(param : T) : R
}