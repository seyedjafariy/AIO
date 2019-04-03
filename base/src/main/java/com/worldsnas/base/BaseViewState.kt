package com.worldsnas.base

import com.worldsnas.mvi.MviViewState

interface BaseViewState : MviViewState {
    val base : BaseState
}