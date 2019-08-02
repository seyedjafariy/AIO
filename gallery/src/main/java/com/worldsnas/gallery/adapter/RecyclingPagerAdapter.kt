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
import androidx.viewpager.widget.PagerAdapter
import java.util.*

/*
 * Created by henrytao on 11/13/15.
 */
abstract class RecyclingPagerAdapter<VH : ViewHolder> : PagerAdapter() {

    abstract val itemCount: Int

    private val mRecycleTypeCaches = SparseArray<RecycleCache>()

    private var mSavedStates = SparseArray<Parcelable?>()

    private val attachedViewHolders: List<ViewHolder>
        get() {
            val attachedViewHolders = ArrayList<ViewHolder>()
            val n = mRecycleTypeCaches.size()
            for (i in 0 until n) {
                for (viewHolder in mRecycleTypeCaches.get(mRecycleTypeCaches.keyAt(i)).mCaches) {
                    if (viewHolder.mIsAttached) {
                        attachedViewHolders.add(viewHolder)
                    }
                }
            }
            return attachedViewHolders
        }

    abstract fun onBindViewHolder(holder: VH, position: Int)

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH

    override fun destroyItem(parent: ViewGroup, position: Int, `object`: Any) {
        if (`object` is ViewHolder) {
            `object`.detach(parent)
        }
    }

    override fun getCount(): Int {
        return itemCount
    }

    override fun getItemPosition(`object`: Any): Int =
        POSITION_NONE


    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val viewType = getItemViewType(position)
        if (mRecycleTypeCaches.get(viewType) == null) {
            mRecycleTypeCaches.put(viewType, RecycleCache(this))
        }
        val viewHolder = mRecycleTypeCaches.get(viewType).getFreeViewHolder(parent, viewType)
        viewHolder.attach(parent, position)
        @Suppress("UNCHECKED_CAST")
        onBindViewHolder(viewHolder as VH, position)
        viewHolder.onRestoreInstanceState(mSavedStates.get(getItemId(position)))
        return viewHolder
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` is ViewHolder && `object`.itemView === view
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
        for (viewHolder in attachedViewHolders) {
            onNotifyItemChanged()
        }
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        if (state is Bundle) {
            val bundle = state as Bundle?
            bundle!!.classLoader = loader
            val ss =
                if (bundle.containsKey(STATE)) bundle.getSparseParcelableArray<Parcelable>(STATE) else null
            mSavedStates = ss ?: SparseArray()
        }
        super.restoreState(state, loader)
    }

    override fun saveState(): Parcelable? {
        val bundle = Bundle()
        for (viewHolder in attachedViewHolders) {
            mSavedStates.put(getItemId(viewHolder.mPosition), viewHolder.onSaveInstanceState())
        }
        bundle.putSparseParcelableArray(STATE, mSavedStates)
        return bundle
    }

    private fun getItemId(position: Int): Int =
        position


    private fun getItemViewType(
        @Suppress("UNUSED_PARAMETER")
        position: Int
    ): Int = 0

    private fun onNotifyItemChanged() {}

    private class RecycleCache internal constructor(
        private val mAdapter: RecyclingPagerAdapter<*>
    ) {
        val mCaches: MutableList<ViewHolder>

        init {
            mCaches = ArrayList()
        }

        internal fun getFreeViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var i = 0
            var viewHolder: ViewHolder
            val n = mCaches.size
            while (i < n) {
                viewHolder = mCaches[i]
                if (!viewHolder.mIsAttached) {
                    return viewHolder
                }
                i++
            }
            viewHolder = mAdapter.onCreateViewHolder(parent, viewType)
            mCaches.add(viewHolder)
            return viewHolder
        }
    }

    companion object {
        private val STATE = RecyclingPagerAdapter::class.java.simpleName
    }
}