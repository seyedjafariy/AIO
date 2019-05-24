package com.worldsnas.gallery.adapter

import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.worldsnas.gallery.drawee.ZoomableDraweeView
import me.relex.photodraweeview.OnScaleChangeListener
import java.util.*

/*
 * Created by troy379 on 07.12.16.
 */
class ImageViewerAdapter(
    private val context: Context, private val dataSet: List<String>,
    private val imageRequestBuilder: ImageRequestBuilder?,
    private val hierarchyBuilder: GenericDraweeHierarchyBuilder?,
    private val isZoomingAllowed: Boolean
) : RecyclingPagerAdapter<ImageViewerAdapter.ImageViewHolder>() {
    private val holders: HashSet<ImageViewHolder>

    init {
        this.holders = HashSet()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val drawee = ZoomableDraweeView(context)
        drawee.isEnabled = isZoomingAllowed

        val holder = ImageViewHolder(drawee)
        holders.add(holder)

        return holder
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(position)
    }

    override val itemCount: Int = dataSet.size


    fun isScaled(index: Int): Boolean {
        for (holder in holders) {
            if (holder.position == index) {
                return holder.isScaled
            }
        }
        return false
    }

    fun resetScale(index: Int) {
        for (holder in holders) {
            if (holder.position == index) {
                holder.resetScale()
                break
            }
        }
    }

    fun getUrl(position: Int): String {
        return dataSet[position]
    }

    private fun getDraweeControllerListener(drawee: ZoomableDraweeView): BaseControllerListener<ImageInfo> {
        return object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                super.onFinalImageSet(id, imageInfo, animatable)
                if (imageInfo == null) {
                    return
                }
                drawee.update(imageInfo.width, imageInfo.height)
            }
        }
    }

    inner class ImageViewHolder(itemView: View) : ViewHolder(itemView), OnScaleChangeListener {

        var position = -1
        val drawee: ZoomableDraweeView
        var isScaled: Boolean = false

        init {
            drawee = itemView as ZoomableDraweeView
        }

        fun bind(position: Int) {
            this.position = position

            tryToSetHierarchy()
            setController(dataSet[position])

            drawee.setOnScaleChangeListener(this)
        }

        override fun onScaleChange(scaleFactor: Float, focusX: Float, focusY: Float) {
            isScaled = drawee.scale > 1.0f
        }

        fun resetScale() {
            drawee.setScale(1.0f, true)
        }

        private fun tryToSetHierarchy() {
            if (hierarchyBuilder != null) {
                hierarchyBuilder.actualImageScaleType = ScalingUtils.ScaleType.FIT_CENTER
                drawee.hierarchy = hierarchyBuilder.build()
            }
        }

        private fun setController(url: String) {
            val controllerBuilder = Fresco.newDraweeControllerBuilder()
            controllerBuilder.setUri(url)
            controllerBuilder.oldController = drawee.controller
            controllerBuilder.controllerListener = getDraweeControllerListener(drawee)
            if (imageRequestBuilder != null) {
                imageRequestBuilder.setSource(Uri.parse(url))
                controllerBuilder.imageRequest = imageRequestBuilder.build()
            }
            drawee.controller = controllerBuilder.build()
        }

    }
}
