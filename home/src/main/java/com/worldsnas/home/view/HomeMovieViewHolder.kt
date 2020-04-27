package com.worldsnas.home.view

import android.net.Uri
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.base.getDisplaySize
import com.worldsnas.base.BaseViewHolder
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.home.HomeIntent
import com.worldsnas.home.R2
import com.worldsnas.home.model.MovieUIModel
import io.reactivex.Observable
import kotlin.math.roundToInt


class HomeMovieViewHolder(
    view: View
) : BaseViewHolder<MovieUIModel, HomeIntent>(view) {

    @BindView(R2.id.poster)
    lateinit var poster: SimpleDraweeView
    @BindView(R2.id.title)
    lateinit var title: TextView
    @BindView(R2.id.releaseDate)
    lateinit var releaseDate: TextView

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
    }

    override fun intents(obj: MovieUIModel): Observable<HomeIntent> =
        TODO()
    /*
        itemView.clicks()
            .doOnNext {
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
            .map {
                HomeIntent.LatestMovieClicked(
                    obj,
                    ViewCompat.getTransitionName(poster) ?: "",
                    ViewCompat.getTransitionName(title) ?: "",
                    ViewCompat.getTransitionName(releaseDate) ?: ""
                )
            }
     */
}