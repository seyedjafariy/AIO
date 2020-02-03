package com.worldsnas.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T, U> Flow<T>.listMerge(block: (Flow<T>) -> List<Flow<U>>) =
    block(this)
        .merge()