package com.glib.core.ui.list

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glib.core.ui.view.IView
import com.glib.core.ui.view.ViewHelper

open class GRecyclerView : RecyclerView, IView {
    private val helper: ViewHelper = ViewHelper(this)

    private val layoutManager = LinearLayoutManager(context)
    private val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST)

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

//    init {
//        setLayoutManager(layoutManager)
//    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

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

    fun itemAnimator(animator: ItemAnimator): GRecyclerView {
        itemAnimator = animator
        return this
    }

    fun reverse(): GRecyclerView {
        layoutManager.reverseLayout = true
        return this
    }

    fun getLastCompletelyVisibleItemId(): Long? {
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        return adapter?.getItemId(pos - 1)  // Subtract header
//        return getAdapter().getItemId(pos - 1)  // Subtract header
    }

    fun getLastCompletelyVisibleItemPos(): Int {
        return layoutManager.findLastCompletelyVisibleItemPosition()
    }
}