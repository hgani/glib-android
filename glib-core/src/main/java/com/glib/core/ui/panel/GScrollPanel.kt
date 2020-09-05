package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.glib.core.ui.layout.GScrollView
import com.glib.core.ui.view.ViewHelper

class GScrollPanel: GScrollView<GScrollPanel> {
    private var helper = ViewHelper(this)
    val contentView = GVerticalPanel(context)

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

    override fun width(width: Int?): GScrollPanel {
        super.width(width)
        contentView.width(width)
        return self()
    }

    override fun height(height: Int?): GScrollPanel {
        super.height(height)
        contentView.height(height)
        return self()
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
