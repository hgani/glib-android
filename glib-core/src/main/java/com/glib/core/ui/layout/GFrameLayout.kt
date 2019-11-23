package com.glib.core.ui.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.glib.core.ui.view.IView
import com.glib.core.ui.view.ViewHelper

open class GFrameLayout<T : GFrameLayout<T>>: FrameLayout, IView {
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

    private fun self(): T {
        return this as T
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
        helper.bgColor(color)
        return self()
    }

    fun bgColor(color: String): T {
        helper.bgColor(color)
        return self()
    }

    fun bg(drawable: Drawable): T {
        helper.bg(drawable)
        return self()
    }

    fun append(child: View): T {
        addView(child)
        return self()
    }

    fun id(id: Int): T {
        this.id = id
        return self()
    }
}
