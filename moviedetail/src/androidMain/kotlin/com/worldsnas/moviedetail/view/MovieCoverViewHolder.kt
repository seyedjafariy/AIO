package com.worldsnas.moviedetail.view

import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.core.view.ViewCompat
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.jakewharton.rxbinding3.view.clicks
import com.worldsnas.base.getDisplaySize
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.moviedetail.MovieDetailIntent
import com.worldsnas.moviedetail.R
import com.worldsnas.moviedetail.databinding.RowRecommendationMovieBinding
import com.worldsnas.moviedetail.model.MovieUIModel
import io.reactivex.Observable
import kotlin.math.roundToInt

class MovieCoverViewHolder(
    view: View
) : BaseViewHolder<MovieUIModel, MovieDetailIntent>(view) {

    private val binding = RowRecommendationMovieBinding.bind(view)

    private val poster: SimpleDraweeView = binding.poster
    private val title: TextView = binding.title
    private val releaseDate: TextView = binding.releaseDate

    override fun bind(obj: MovieUIModel) {
        val width = itemView.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = obj.poster posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        poster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(poster.controller)
            .setImageRequest(request)
            .build()
        title.text = obj.title
        releaseDate.text = obj.releaseDate

        ViewCompat.setTransitionName(
            poster,
            itemView
                .context
                .getString(
                    R.string.transition_img_1_id,
                    adapterPosition
                )
        )
        ViewCompat.setTransitionName(
            title,
            itemView
                .context
                .getString(
                    R.string.transition_txt_1_id,
                    adapterPosition
                )
        )
        ViewCompat.setTransitionName(
            releaseDate,
            itemView
                .context
                .getString(
                    R.string.transition_txt_2_id,
                    adapterPosition
                )
        )
    }

    override fun intents(obj: MovieUIModel): Observable<MovieDetailIntent> =
        itemView.clicks()
            .map {
                MovieDetailIntent.RecommendationClicked(
                    obj,
                    ViewCompat.getTransitionName(poster) ?: "",
                    ViewCompat.getTransitionName(title) ?: "",
                    ViewCompat.getTransitionName(releaseDate) ?: ""
                )
            }
}