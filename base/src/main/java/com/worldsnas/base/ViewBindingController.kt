package com.worldsnas.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class ViewBindingController<T : ViewBinding> @JvmOverloads constructor(
    bundle: Bundle? = null
) : RefWatchingController(bundle) {

    private var _binding: T? = null
    val binding: T
        get() = _binding!!

    abstract fun bindView(inflater: LayoutInflater, container: ViewGroup): T

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        _binding = bindView(inflater, container)
        onViewBound(binding)
        return binding.root
    }

    open fun onViewBound(binding: T) {}

    override fun onDestroyView(view: View) {
        _binding = null
        super.onDestroyView(view)
    }

}