package com.gani.lib.ui.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout

import com.gani.lib.ui.view.ViewHelper

open class GRelativeLayout<T : GRelativeLayout<T>>: RelativeLayout {
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

    fun width(width: Int?): T {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): T {
        helper.height(height)
        return self()
    }

    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): T {
        helper.padding(left, top, right, bottom)
        return self()
    }

    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): T {
        helper.margin(left, top, right, bottom)
        return self()
    }

    fun gravity(gravity: Int): T {
        setGravity(gravity)
        return self()
    }

    fun bgColor(color: Int): T {
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
}
