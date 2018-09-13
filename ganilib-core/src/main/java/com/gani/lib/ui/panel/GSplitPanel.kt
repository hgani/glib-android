package com.gani.lib.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gani.lib.ui.layout.GRelativeLayout
import com.gani.lib.ui.view.ViewHelper

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
        // Need to wrap the views so that we can set their width.
        val l = GVerticalPanel(context).append(left)
        val r = GVerticalPanel(context).append(right)

        addView(l)
        addView(center)
        addView(r)

        ViewHelper(l).relative {
            it.alignLeft()
        }

        ViewHelper(center).relative {
            it.rightOf(l)
            it.leftOf(r)
        }

        ViewHelper(r).relative {
            it.alignRight()
        }

        return self()
    }
}
