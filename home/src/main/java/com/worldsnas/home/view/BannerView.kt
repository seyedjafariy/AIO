package com.worldsnas.home.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.worldsnas.androidcore.getScreenWidth
import com.worldsnas.domain.helpers.coverFullUrl
import com.worldsnas.home.databinding.BannerViewBinding
import com.worldsnas.view_component.Movie

@ModelView(
    autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT,
    saveViewState = true
)
class BannerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : LinearLayout(context, attributeSet){

    private val binding = BannerViewBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    @ModelProp
    lateinit var movie : Movie

    @AfterPropsSet
    fun bindView(){
        binding.image.setImageURI(movie.cover.coverFullUrl(context.getScreenWidth()))
    }
}