package com.worldsnas.home

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState

data class HomeState(
    override val base: BaseState
) : BaseViewState {
    companion object {
        fun startingState() = HomeState(base = BaseState.showError("اصغر"))
    }
}