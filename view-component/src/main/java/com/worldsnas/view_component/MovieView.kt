package com.worldsnas.view_component

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.*
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.androidcore.getDisplaySize
import com.worldsnas.domain.helpers.posterFullUrl
import com.worldsnas.view_component.databinding.MovieViewBinding
import kotlin.math.roundToInt

@ModelView(defaultLayout = R2.layout.movie_view, saveViewState = true)
class MovieView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {
    private val binding: MovieViewBinding =
        MovieViewBinding.inflate(LayoutInflater.from(context), this, false)

    @ModelProp
    lateinit var movie: Movie

    fun poster(posterUrl: String) {
        val width = binding.root.getDisplaySize().width / 3
        val height = (width * 1.5).roundToInt()

        val url = posterUrl posterFullUrl width

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions.forDimensions(width, height))
            .build()
        binding.poster.controller = Fresco.newDraweeControllerBuilder()
            .setOldController(binding.poster.controller)
            .setImageRequest(request)
            .build()
    }

    fun title(title: CharSequence) {
        binding.title.text = title
    }

    fun releaseDate(date: CharSequence) {
        binding.releaseDate.text = date
    }

    @AfterPropsSet
    fun movieModel() {
        poster(movie.poster)
        title(movie.title)
        releaseDate(movie.releaseDate)
    }

    @CallbackProp
    fun listener(listener: ((movie: Movie) -> Unit)?) {
        if (listener == null) {
            binding.root.setOnClickListener(null)
        } else {
            binding.root.setOnClickListener {
                listener(movie)
            }
        }
    }
}

data class Movie(
    val id: Long,
    val poster: String,
    val cover: String,
    val title: String,
    val releaseDate: String
)
