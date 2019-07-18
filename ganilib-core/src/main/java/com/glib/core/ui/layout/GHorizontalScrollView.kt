package com.glib.core.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.HorizontalScrollView
import com.glib.core.ui.view.ViewHelper

open class GHorizontalScrollView<T : GHorizontalScrollView<T>> : HorizontalScrollView, ILayout {
    private val helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Nothing to do
    }

    override fun width(width: Int?): T {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): T {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): T {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): T {
        helper.bgColor(code)
        return self()
    }

    private fun self(): T {
        return this as T
    }

    override fun append(child: View): T {
        addView(child)
        return self()
    }
}
