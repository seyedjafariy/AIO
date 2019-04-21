package com.worldsnas.base

import android.os.Bundle
import com.bluelinelabs.conductor.Controller

abstract class ButterKnifeController @JvmOverloads constructor(
    bundle: Bundle? = null
) : Controller(bundle) {
    /*
    private var unbinder: Unbinder? = null

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        unbinder = ButterKnife.bind(this, view)
        onViewBound(view)
        return view
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun onViewBound(view: View) {}

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        unbinder?.unbind()
        unbinder = null
    }
    */
}