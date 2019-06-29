package com.worldsnas.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife

open class ButterKnifeViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    init {
        ButterKnife.bind(this, view)
    }

}