package com.gani.lib.ui.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

open class GRecyclerView : RecyclerView {
    private val layoutManager = LinearLayoutManager(context)
    private val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    init {
        setLayoutManager(layoutManager)
    }

    fun adapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>): GRecyclerView {
        this.adapter = adapter
        return this
    }

    fun separator(separator: Boolean): GRecyclerView {
        if (separator) {
            addItemDecoration(divider)
        }
        else {
            removeItemDecoration(divider)
        }
        return this
    }

    fun reverse(): GRecyclerView {
        layoutManager.reverseLayout = true
        return this
    }

    fun getLastCompletelyVisibleItemId(): Long {
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        return getAdapter().getItemId(pos - 1)  // Subtract header
    }
}