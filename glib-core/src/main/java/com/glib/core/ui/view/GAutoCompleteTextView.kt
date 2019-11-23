package com.glib.core.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

class GAutoCompleteTextView : AppCompatAutoCompleteTextView, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GAutoCompleteTextView {
        return this
    }

    override fun width(width: Int?): GAutoCompleteTextView {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GAutoCompleteTextView {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GAutoCompleteTextView {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GAutoCompleteTextView {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(res: Int): GAutoCompleteTextView {
        helper.bgColor(res)
        return self()
    }
}
