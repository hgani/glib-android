package com.glib.core.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import com.glib.core.utils.Res
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class GTextInputEditText : TextInputEditText, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GTextInputEditText {
        return this
    }

    override fun width(width: Int?): GTextInputEditText {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GTextInputEditText {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GTextInputEditText {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GTextInputEditText {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GTextInputEditText {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): GTextInputEditText {
        return bgColor(Res.color(code))
    }

    fun text(text: String): GTextInputEditText {
        setText(text);
        return this
    }

    fun hint(hint: String): GTextInputEditText {
        this.hint = hint
        return this
    }

    fun inputType(type: Int): GTextInputEditText {
        this.inputType = type
        return this
    }

    fun typeface(typeface: Typeface): GTextInputEditText {
        setTypeface(typeface)
        return this
    }

    fun textColor(color: Int): GTextInputEditText {
        setTextColor(color)
        return self()
    }

//    fun append(view: View): GTextInputEditText {
//        addView(view)
//        return self()
//    }
}
