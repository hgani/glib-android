package com.gani.lib.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatRadioButton

class GRadioButton : AppCompatRadioButton, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun width(width: Int?): GRadioButton {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GRadioButton {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GRadioButton {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GRadioButton {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GRadioButton {
        helper.bgColor(color)
        return self()
    }

    fun text(text: String): GRadioButton {
        this.text = text
        return self()
    }

    fun tag(tag: Any): GRadioButton {
        this.tag = tag
        return self()
    }

    fun generatedId(): GRadioButton {
        this.id = View.generateViewId()
        return self()
    }

    private fun self(): GRadioButton {
        return this
    }
}
