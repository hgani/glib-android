package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.glib.core.ui.layout.GHorizontalScrollView
import com.glib.core.ui.view.ViewHelper

class GHorizontalScrollPanel: GHorizontalScrollView<GHorizontalScrollPanel> {
    private var helper = ViewHelper(this)
    private val contentView = GHorizontalPanel(context)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        addView(contentView)
    }

    private fun self(): GHorizontalScrollPanel {
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GHorizontalScrollPanel {
        contentView.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GHorizontalScrollPanel {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun append(child: View): GHorizontalScrollPanel {
        contentView.append(child)
        return self()
    }
}
