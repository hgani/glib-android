package com.glib.core.ui.view

import android.content.Context
//import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatCheckBox

class GCheckBox : AppCompatCheckBox, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun width(width: Int?): GCheckBox {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GCheckBox {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GCheckBox {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GCheckBox {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GCheckBox {
        helper.bgColor(color)
        return self()
    }

    fun text(text: String): GCheckBox {
        this.text = text
        return self()
    }

    fun tag(tag: Any): GCheckBox {
        this.tag = tag
        return self()
    }

    fun generatedId(): GCheckBox {
        this.id = View.generateViewId()
        return self()
    }

    fun onChange(listener: (GCheckBox, Boolean) -> Unit): GCheckBox {
        setOnCheckedChangeListener { button, boolean ->
            listener(this, boolean)
        }
        return self()
    }

    private fun self(): GCheckBox {
        return this
    }
}
