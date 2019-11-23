package com.glib.qr

import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView
import com.glib.core.ui.view.IView
import com.glib.core.ui.view.ViewHelper


open class GSurfaceView: SurfaceView, IView {
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

    private fun self(): GSurfaceView {
        return this
    }

    override fun width(width: Int?): GSurfaceView {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GSurfaceView {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GSurfaceView {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GSurfaceView {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GSurfaceView {
        helper.bgColor(color)
        return self()
    }
}
