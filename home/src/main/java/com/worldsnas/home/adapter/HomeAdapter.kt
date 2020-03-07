package com.worldsnas.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.worldsnas.androidcore.inflate
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.HomeState
import com.worldsnas.home.R
import com.worldsnas.home.model.HomeUIModel
import com.worldsnas.home.view.HomeMovieViewHolder
import com.worldsnas.home.view.SliderViewHolder
import com.worldsnas.mvi.MviPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeAdapter @Inject constructor(
    private val presenter: MviPresenter<HomeIntent, HomeState>,
    diffCallback: HomeUIDiffCallback
) : ListAdapter<HomeUIModel, RecyclerView.ViewHolder>(diffCallback),
    BaseSliderView.OnSliderClickListener {

    private var disposables = CompositeDisposable()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            0 ->
                SliderViewHolder(parent.inflate(R.layout.row_slider_layout))
            1 ->
                HomeMovieViewHolder(parent.inflate(R.layout.row_home_movie))
            else ->
                throw IllegalArgumentException("there should be only two view types in home view")

        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SliderViewHolder ->
                (getItem(position) as HomeUIModel.Slider).apply {
                    holder.bind(slides, this@HomeAdapter)
                }
            is HomeMovieViewHolder -> {
                (getItem(position) as HomeUIModel.LatestMovie).apply {
                    holder.bind(movie)
//                    holder.intents(movie)
//                        .subscribeBy {
//                            presenter.processIntents(it)
//                        }
//                        .addTo(disposables)
                }
            }
            else ->
                throw IllegalArgumentException("there should be only two view types in home view")
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is HomeUIModel.Slider -> 0
            is HomeUIModel.LatestMovie -> 1
            else ->
                throw IllegalArgumentException("there should be only two views in home view")
        }


    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is SliderViewHolder ->
                holder.startAutoCycle()
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is SliderViewHolder ->
                holder.stopAutoCycle()
        }
        super.onViewDetachedFromWindow(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        disposables.clear()
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onSliderClick(slider: BaseSliderView) {
        val id = slider.bundle.getLong("id")
        presenter.processIntents(
            HomeIntent.SliderClicked(
                id
            )
        )
    }
}