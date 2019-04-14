package com.worldsnas.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.subjects.PublishSubject
import kotlinx.android.extensions.LayoutContainer

open class BaseViewHolder<in T : Any?, R>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    open fun bind(obj : T){
    }

    open fun intents(subject : PublishSubject<R>){
    }
}