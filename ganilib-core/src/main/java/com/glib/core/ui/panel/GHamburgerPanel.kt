package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.glib.core.ui.layout.GRelativeLayout
import com.glib.core.ui.view.ViewHelper

open class GHamburgerPanel: GRelativeLayout<GHamburgerPanel> {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Nothing to do
    }

    private fun self(): GHamburgerPanel {
        return this
    }

    fun withViews(top: View, middle: View, bottom: View): GHamburgerPanel {
        addView(top)
        addView(middle)
        addView(bottom)

        ViewHelper(top).relative {
            it.alignTop()
        }

        ViewHelper(middle).relative {
            it.below(top)
            it.above(bottom)
        }

        ViewHelper(bottom).relative {
            it.alignBottom()
        }

        return self()
    }
}
