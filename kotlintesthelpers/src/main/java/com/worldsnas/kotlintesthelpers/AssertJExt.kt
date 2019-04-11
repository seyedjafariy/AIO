package com.worldsnas.kotlintesthelpers

import org.assertj.core.api.SoftAssertions

fun softAssert(block : SoftAssertions.()->Unit){
    val soft = SoftAssertions()
    block(soft)
    soft.assertAll()
}