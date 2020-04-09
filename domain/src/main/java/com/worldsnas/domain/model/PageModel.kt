package com.worldsnas.domain.model

sealed class PageModel {
    object First : PageModel()

    sealed class NextPage : PageModel() {
        class LastId(
            val lastID: Long
        ) : NextPage()

        class Total(
            val total: Int
        ) : NextPage()

        object Next : NextPage()
    }
}