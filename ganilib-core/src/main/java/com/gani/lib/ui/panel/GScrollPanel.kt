package com.gani.lib.ui.panel

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.view.GScrollView

class GScrollPanel: GScrollView<GScrollPanel> {
    private val contentView = GLinearLayout(context).vertical()

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

    override fun paddings(l: Int?, t: Int?, r: Int?, b: Int?): GScrollPanel {
        contentView.paddings(l, t, r, b)
        return self()
    }

    override fun append(child: View): GScrollPanel {
        contentView.append(child)
        return self()
    }
}
