package com.worldsnas.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder

abstract class ButterKnifeController @JvmOverloads constructor(
    bundle: Bundle? = null
) : RefWatchingController(bundle) {

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
}