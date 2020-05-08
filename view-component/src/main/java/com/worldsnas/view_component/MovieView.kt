package com.worldsnas.view_component

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.base.getDisplaySize
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.view_component.databinding.MovieViewBinding
import kotlin.math.roundToInt

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class MovieView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet) {
    private val binding: MovieViewBinding =
        MovieViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var title: String = ""
    private var poster: String = ""
    private var releaseDate : String = ""
    private var movieId: Long = 0

    @ModelProp
    fun poster(posterUrl: String) {
        poster = posterUrl
        val width = binding.root.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = posterUrl posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        binding.moviePoster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(binding.moviePoster.controller)
            .setImageRequest(request)
            .build()
    }

    @ModelProp
    fun movieId(id: Long) {
        this.movieId = id
    }

    @TextProp
    fun title(title: CharSequence) {
        this.title = title.toString()
        binding.movieTitle.text = title
    }

    @TextProp
    fun releaseDate(date: CharSequence) {
        releaseDate = date.toString()
        binding.movieReleaseDate.text = date
    }

    @CallbackProp
    fun listener(listener: ((movieId: Long, title: String, poster : String, releaseData : String) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(movieId, title, poster, releaseDate)
            }
        }
    }
}