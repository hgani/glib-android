package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.glib.core.ui.layout.GRelativeLayout
import com.glib.core.ui.view.IView
import com.glib.core.ui.view.ViewHelper

open class GSplitPanel: GRelativeLayout<GSplitPanel> {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Nothing to do
    }

    private fun self(): GSplitPanel {
        return this
    }

    fun withViews(left: View, center: View, right: View): GSplitPanel {
        // Wrap all the elements so they use linear layout params (instead of relative) so that we can set
        // the view's width/height in a consistent manner.
        val l = GVerticalPanel(context).append(left)
        val r = GVerticalPanel(context).append(right)
        val c = GVerticalPanel(context).append(center)

        // Center view should always stretch.
        (center as? IView)?.width(ViewGroup.LayoutParams.MATCH_PARENT)

        addView(l)
        addView(c)
        addView(r)

        ViewHelper(l).relative {
            it.alignLeft()
        }

        ViewHelper(c).relative {
            it.rightOf(l)
            it.leftOf(r)
        }

        ViewHelper(r).relative {
            it.alignRight()
        }

        return self()
    }
}
