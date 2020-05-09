package com.worldsnas.navigation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

inline fun <R, T : Screens> Flow<T>.navigateTo(navigator: Navigator) : Flow<R> = transform {
    navigator.goTo(it)
}