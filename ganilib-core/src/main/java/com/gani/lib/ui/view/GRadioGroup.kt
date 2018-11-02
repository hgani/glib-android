package com.gani.lib.ui.view

import android.content.Context
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup

class GRadioGroup : RadioGroup, IView {
    private var helper: ViewHelper = ViewHelper(this)

    private var customClickSound: MediaPlayer? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.helper = ViewHelper(this)
    }

    override fun width(width: Int?): GRadioGroup {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GRadioGroup {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GRadioGroup {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GRadioGroup {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GRadioGroup {
        helper.bgColor(color)
        return self()
    }

    fun append(view: View): GRadioGroup {
        addView(view)
        return self()
    }

    private fun self(): GRadioGroup {
        return this
    }
}
