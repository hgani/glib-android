package com.glib.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar

open class GProgressBar : ProgressBar {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GProgressBar {
        return this
    }

    fun width(width: Int?): GProgressBar {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): GProgressBar {
        helper.height(height)
        return self()
    }
}
