/*
 * Copyright 2015 "Henry Tao <hi@henrytao.me>"
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.worldsnas.gallery.adapter

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup

/*
 * Created by henrytao on 11/13/15.
 */
abstract class ViewHolder(val itemView: View) {

    internal var mIsAttached: Boolean = false

    internal var mPosition: Int = 0

    internal fun attach(parent: ViewGroup, position: Int) {
        mIsAttached = true
        mPosition = position
        parent.addView(itemView)
    }

    internal fun detach(parent: ViewGroup) {
        parent.removeView(itemView)
        mIsAttached = false
    }

    internal fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val ss = if (state.containsKey(STATE)) state.getSparseParcelableArray<Parcelable>(STATE) else null
            if (ss != null) {
                itemView.restoreHierarchyState(ss)
            }
        }
    }

    internal fun onSaveInstanceState(): Parcelable {
        val state = SparseArray<Parcelable>()
        itemView.saveHierarchyState(state)
        val bundle = Bundle()
        bundle.putSparseParcelableArray(STATE, state)
        return bundle
    }

    companion object {

        private val STATE = ViewHolder::class.java.simpleName
    }
}