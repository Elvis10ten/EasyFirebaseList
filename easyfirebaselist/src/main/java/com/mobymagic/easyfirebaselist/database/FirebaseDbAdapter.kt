package com.mobymagic.easyfirebaselist.database

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError

internal class FirebaseDbAdapter<D, VH: RecyclerView.ViewHolder>(
        options: FirebaseRecyclerOptions<D>,
        private val adapterCallback: AdapterCallback<D, VH>): FirebaseRecyclerAdapter<D, VH>(options) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VH {
        return adapterCallback.onCreateViewHolder(LayoutInflater.from(viewGroup.context), viewGroup)
    }

    override fun onBindViewHolder(viewHolder: VH, position: Int, model: D) {
        val refKey = getRef(position).key

        viewHolder.itemView.setOnClickListener { adapterCallback.onItemClicked(viewHolder, refKey, model) }
        adapterCallback.onBindViewHolder(viewHolder, refKey, model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        adapterCallback.onDataChanged(itemCount == 0)
    }

    override fun onError(error: DatabaseError) {
        super.onError(error)
        adapterCallback.onError(error)
    }

    interface AdapterCallback<in D, VH> {

        fun onCreateViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup): VH
        fun onBindViewHolder(viewHolder: VH, key: String, model: D)
        fun onItemClicked(viewHolder: VH, key: String, model: D)
        fun onError(error: DatabaseError)
        fun onDataChanged(isEmpty: Boolean)

    }

}