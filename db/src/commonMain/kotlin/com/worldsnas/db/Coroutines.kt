package com.worldsnas.db

import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T) : T