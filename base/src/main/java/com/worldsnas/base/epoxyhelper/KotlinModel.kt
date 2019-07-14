package com.worldsnas.base.epoxyhelper

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import butterknife.Unbinder
import com.airbnb.epoxy.EpoxyModel
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class KotlinModel(
    @LayoutRes private val layoutRes: Int
) : EpoxyModel<View>() {

    var view: View? = null
    private var unBinder : Unbinder? = null

    abstract fun viewBound(view : View)

    override fun bind(view: View) {
        unBinder = ButterKnife.bind(this, view)
        this.view = view
        viewBound(view)
    }

    override fun unbind(view: View) {
        unBinder?.unbind()
        unBinder = null
        this.view = null
    }

    override fun getDefaultLayout() = layoutRes

    protected fun <V : View> bind(@IdRes id: Int) = object : ReadOnlyProperty<KotlinModel, V> {
        override fun getValue(thisRef: KotlinModel, property: KProperty<*>): V {
            // This is not efficient because it looks up the view by id every time (it loses
            // the pattern of a "holder" to cache that look up). But it is simple to use and could
            // be optimized with a map
            @Suppress("UNCHECKED_CAST")
            return view?.findViewById(id) as V?
                    ?: throw IllegalStateException("View ID $id for '${property.name}' not found.")
        }
    }
}