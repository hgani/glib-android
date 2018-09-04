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
        addView(left)
        addView(center)
        addView(right)

        ViewHelper(left).relative {
            it.alignLeft()
        }

        ViewHelper(center).relative {
            it.rightOf(left)
            it.leftOf(right)
        }

        ViewHelper(right).relative {
            it.alignRight()
        }

        return self()
    }
}
