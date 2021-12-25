package com.glib.core.ui.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import com.glib.core.utils.Res
import com.google.android.material.textfield.TextInputLayout


const val CORNER_RADIUS = 16f

class GTextInputLayout : TextInputLayout, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GTextInputLayout {
        return this
    }

    override fun width(width: Int?): GTextInputLayout {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GTextInputLayout {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GTextInputLayout {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GTextInputLayout {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GTextInputLayout {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): GTextInputLayout {
        return bgColor(Res.color(code))
    }

    fun append(view: View): GTextInputLayout {
        addView(view)
        return self()
    }

    fun outlined(): GTextInputLayout {
        boxBackgroundMode = TextInputLayout.BOX_BACKGROUND_OUTLINE
        setBoxCornerRadii(CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS)
        boxBackgroundColor = Color.TRANSPARENT

        return self()
    }
}
