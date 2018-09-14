package com.gani.lib.ui.list

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.gani.lib.ui.view.IView
import com.gani.lib.ui.view.ViewHelper

open class GRecyclerView : RecyclerView, IView {
    private val helper: ViewHelper = ViewHelper(this)

    private val layoutManager = LinearLayoutManager(context)
    private val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    init {
        setLayoutManager(layoutManager)
    }

    override fun width(width: Int?): GRecyclerView {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GRecyclerView {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GRecyclerView {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GRecyclerView {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GRecyclerView {
        setBackgroundColor(color)
        return this
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