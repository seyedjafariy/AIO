package com.worldsnas.search

import com.worldsnas.base.BaseState
import com.worldsnas.base.BaseViewState

data class SearchState(
    override val base: BaseState
) : BaseViewState {
    companion object {
        fun idle() =
            SearchState(
                base = BaseState.stable()
            )
    }
}