package com.gani.lib.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.utils.Res

abstract class AbstractBindingHolder private constructor(val container: GLinearLayout, selectable: Boolean) : RecyclerView.ViewHolder(container) {
    val context: Context
        get() = container.context

    init {
        if (selectable) {
            unhighlightSelectable()
        }
    }

    protected fun inflate(parent: ViewGroup, layoutId: Int): ViewGroup {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as ViewGroup
    }

    protected constructor(context: Context, selectable: Boolean) : this(GLinearLayout(context).vertical().width(ViewGroup.LayoutParams.MATCH_PARENT), selectable) {}

//    protected fun highlightSelectable() {
//        Res.drawable(R.drawable.background_post_highlight_selector)?.let { container.bg(drawable = it) }
//    }

    protected fun unhighlightSelectable() {
        // See http://stackoverflow.com/questions/8732662/how-to-set-background-highlight-to-a-linearlayout/28087443#28087443
        val outValue = TypedValue()
        Res.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        container.setBackgroundResource(outValue.resourceId)
    }
}
