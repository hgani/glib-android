package com.gani.lib.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gani.lib.utils.Res

class GView : View, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun width(width: Int?): GView {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GView {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GView {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GView {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GView {
        helper.bgColor(color)
        return this
    }

    fun bgColor(code: String): GView {
        setBackgroundColor(Res.color(code))
        return this
    }

    fun bg(res: Int): GView {
        helper.bg(res)
        return this
    }
}
