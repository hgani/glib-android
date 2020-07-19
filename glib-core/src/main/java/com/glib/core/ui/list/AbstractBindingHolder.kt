package com.glib.core.ui.list

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.utils.Res

// TODO: Remove selectable from constructor. They should be set dynamically.
abstract class AbstractBindingHolder private constructor(val container: GVerticalPanel, selectable: Boolean) : RecyclerView.ViewHolder(container) {
    val context: Context
        get() = container.context

    var selectable: Boolean = true
        get() = field
        set(value) {
            field = value

            if (field) {
                unhighlightSelectable()
            } else {
                container.setBackgroundResource(0)
            }
        }

    init {
        this.selectable = selectable
//        if (selectable) {
//            unhighlightSelectable()
//        }
    }

    protected fun inflate(parent: ViewGroup, layoutId: Int): ViewGroup {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false) as ViewGroup
    }

    protected constructor(context: Context, selectable: Boolean) : this(GVerticalPanel(context).width(ViewGroup.LayoutParams.MATCH_PARENT), selectable) {}

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
