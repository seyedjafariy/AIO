package com.daimajia.slider.library

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

internal class InfiniteScroller(
    private val layoutManager: LinearLayoutManager,
    private val size: Int
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dx > 0) {
            val lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()
            if (size - 2 == lastPosition) {
                layoutManager.scrollToPosition(4)
                return
            }
            if (size - 1 == lastPosition) {
                layoutManager.scrollToPosition(5)
                return
            }
        }

        if (dx < 0) {
            val firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (1 == firstPosition) {
                layoutManager.scrollToPosition(size - 5)
                return
            }
            if (0 == firstPosition) {
                layoutManager.scrollToPosition(size - 6)
                return
            }
        }
    }
}