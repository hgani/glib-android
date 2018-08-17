package com.gani.lib.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gani.lib.ui.view.GScrollView
import com.gani.lib.ui.view.ViewHelper

class GScrollPanel: GScrollView<GScrollPanel> {
    private var helper = ViewHelper(this)
    private val contentView = GVerticalPanel(context)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        addView(contentView)
    }

    private fun self(): GScrollPanel {
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GScrollPanel {
        contentView.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GScrollPanel {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun append(child: View): GScrollPanel {
        contentView.append(child)
        return self()
    }
}
