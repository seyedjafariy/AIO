package com.worldsnas.aio

fun String.toNumber() : String{
    var temp = ""
    toCharArray().forEach {
        when (it) {
            '0','1','2','3','4','5','6','7','8','9' ->
                temp += it
        }
    }
    return temp
}