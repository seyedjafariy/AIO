package com.worldsnas.base

import android.view.View
import io.reactivex.subjects.PublishSubject

open class BaseViewHolder<in T : Any?, R>(
    val view: View
) : ButterKnifeViewHolder(view) {

    open fun bind(obj : T){
    }

    open fun intents(subject : PublishSubject<R>){
    }
}