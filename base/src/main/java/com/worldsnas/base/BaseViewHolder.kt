package com.worldsnas.base

import android.view.View
import io.reactivex.Completable
import io.reactivex.Observable

open class BaseViewHolder<in T : Any?, R>(
        val view: View
) : ButterKnifeViewHolder(view) {

    open fun bind(obj: T) {
    }

    open fun intents(obj : T): Observable<R> {
        return Completable.complete().toObservable()
    }
}