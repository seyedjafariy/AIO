package com.worldsnas.base

import com.worldsnas.core.mvi.BaseState
import com.worldsnas.mvi.MviViewState

interface BaseViewStateOld : MviViewState {
    val base : BaseState
}