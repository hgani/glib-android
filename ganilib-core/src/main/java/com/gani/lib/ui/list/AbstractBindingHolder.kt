package com.gani.lib.ui.list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gani.lib.R
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.utils.Res

abstract class AbstractBindingHolder private constructor(val layout: ViewGroup, selectable: Boolean) : RecyclerView.ViewHolder(layout) {
    val context: Context
        get() = layout.context

    init {
        if (selectable) {
            unhighlightSelectable()
        }
    }

//    companion object {
//        private fun attach(layout: ViewGroup, parent: ViewGroup): ViewGroup {
////            parent.addView(layout)
//            return layout
//        }
//    }

//    protected constructor(parent: ViewGroup, layoutId: Int, selectable: Boolean) : this(LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as ViewGroup, selectable) {}

    protected fun inflate(parent: ViewGroup, layoutId: Int): ViewGroup {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as ViewGroup
    }

    protected constructor(context: Context, selectable: Boolean) : this(GLinearLayout(context).vertical().width(ViewGroup.LayoutParams.MATCH_PARENT), selectable) {}

    protected fun unselectable() {
        layout.setBackgroundDrawable(Res.drawable(R.color.transparent))
    }

    protected fun highlightSelectable() {
        layout.setBackgroundDrawable(Res.drawable(R.drawable.background_post_highlight_selector))
    }

    protected fun unhighlightSelectable() {
        // See http://stackoverflow.com/questions/8732662/how-to-set-background-highlight-to-a-linearlayout/28087443#28087443
        val outValue = TypedValue()
        Res.context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
        layout.setBackgroundResource(outValue.resourceId)
    }

}
