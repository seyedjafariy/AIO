package com.worldsnas.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.worldsnas.core.inflate
import com.worldsnas.home.R

class HomeMoviesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        object : RecyclerView.ViewHolder(parent.inflate(R.layout.row_home_movie)) {

        }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}