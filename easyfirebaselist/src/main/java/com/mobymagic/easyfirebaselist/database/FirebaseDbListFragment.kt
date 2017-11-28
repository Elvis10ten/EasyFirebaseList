package com.mobymagic.easyfirebaselist.database

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.mobymagic.easyfirebaselist.EmptyStyle
import com.mobymagic.easyfirebaselist.ErrorStyle
import com.mobymagic.easyfirebaselist.ProgressStyle
import com.mobymagic.easyfirebaselist.R
import kotlinx.android.synthetic.main.fragment_firebase_db_list.*

/**
 * A simple [Fragment] that allows you to easily display a list of data from the Firebase database
 */
abstract class FirebaseDbListFragment<D, VH : RecyclerView.ViewHolder>: Fragment(),
        FirebaseDbAdapter.AdapterCallback<D, VH> {

    private lateinit var mAdapter: FirebaseDbAdapter<D, VH>

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_firebase_db_list, container, false)
    }

    final override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(recyclerView)

        // Set up FirebaseRecyclerAdapter with the Query
        val query = getQuery()
        val options = FirebaseRecyclerOptions.Builder<D>()
                .setQuery(query, getDataClass())
                .build()

        mAdapter = FirebaseDbAdapter(options, this)
        recyclerView.adapter = mAdapter

        listenForData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopListeningForData()
    }

    private fun listenForData() {
        showProgressView(getProgressStyle().progressMessage)
        mAdapter.startListening()
    }

    private fun stopListeningForData() {
        mAdapter.stopListening()
    }

    override fun onDataChanged(isEmpty: Boolean) {
        if(isEmpty) onDataEmpty() else hideProgressView()
    }

    private fun onDataEmpty() {
        showEmptyView()
    }

    override fun onError(error: DatabaseError) {
        showErrorView(error)
    }

    private fun showProgressView(message: String) {
        progressContainerView.visibility = View.VISIBLE
        progressMessageTextView.text = message
    }

    private fun hideProgressView() {
        progressContainerView.visibility = View.GONE
    }

    private fun showErrorView(error: DatabaseError) {
        val errorStyle = getErrorStyle(error)
        errorContainerView.visibility = View.VISIBLE
        errorIconImageView.setImageResource(errorStyle.errorIconRes)
        errorIconImageView.setColorFilter(errorStyle.errorIconTintColor, PorterDuff.Mode.MULTIPLY)
        errorMessageTextView.text = errorStyle.errorMessage
        errorMessageTextView.setTextColor(errorStyle.errorMessageTextColor)
    }

    private fun hideErrorView() {
        errorContainerView.visibility = View.GONE
    }

    private fun showEmptyView() {
        val emptyStyle = getEmptyStyle()
        emptyContainerView.visibility = View.VISIBLE
        emptyIconImageView.setImageResource(emptyStyle.emptyIconRes)
        emptyIconImageView.setColorFilter(emptyStyle.emptyIconTintColor, PorterDuff.Mode.MULTIPLY)
        emptyMessageTextView.text = emptyStyle.emptyMessage
        emptyMessageTextView.setTextColor(emptyStyle.emptyMessageTextColor)
    }

    private fun hideEmptyView() {
        emptyContainerView.visibility = View.GONE
    }

    protected abstract fun setupRecyclerView(recyclerView: RecyclerView)
    protected abstract fun getProgressStyle(): ProgressStyle
    protected abstract fun getEmptyStyle(): EmptyStyle
    protected abstract fun getErrorStyle(error: DatabaseError): ErrorStyle

    protected abstract fun getQuery(): Query
    protected abstract fun getDataClass(): Class<D>

    override abstract fun onCreateViewHolder(inflater: LayoutInflater, viewGroup: ViewGroup): VH
    override abstract fun onBindViewHolder(viewHolder: VH, key: String, model: D)
    override abstract fun onItemClicked(viewHolder: VH, key: String, model: D)

}
