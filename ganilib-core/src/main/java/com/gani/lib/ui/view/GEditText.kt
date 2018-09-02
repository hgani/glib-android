package com.gani.lib.ui.view

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.gani.lib.utils.Res

class GEditText : AppCompatEditText, IView {
    private var helper = ViewHelper(this)

    private var onTextChanged: ((GEditText) -> Unit)? = null
    private var textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            // Do nothing
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Do nothing
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged?.invoke(this@GEditText)
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        addTextChangedListener(textWatcher)
    }

    private fun self() : GEditText {
        return this
    }

    fun bgColor(code: String): GEditText {
        bgColor(Res.color(code))
        return self()
    }

    fun bgColor(color: Int): GEditText {
        setBackgroundColor(color)
        return self()
    }

    fun width(width: Int?) : GEditText {
        helper.width(width)
        return self()
    }

    fun height(height: Int?) : GEditText {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?) : GEditText {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?) : GEditText {
        helper.margin(l, t, r, b)
        return self()
    }

    fun onTextChanged(listener: (GEditText) -> Unit) : GEditText {
        this.onTextChanged = listener
        return self()
    }

    fun maxLines(lines: Int) : GEditText {
        this.maxLines = lines
        return self()
    }
}
