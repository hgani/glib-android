package com.glib.core.ui.view

import android.content.Context
import android.content.res.ColorStateList
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import androidx.appcompat.widget.AppCompatSpinner

class GSpinner : AppCompatSpinner, IView {
    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    override fun width(width: Int?): GSpinner {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GSpinner {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GSpinner {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GSpinner {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GSpinner {
        helper.bgColor(color)
        return this
    }

    fun adapter(adapter: SpinnerAdapter): GSpinner {
        setAdapter(adapter)
        return this
    }

    fun bgResource(resId: Int): GSpinner {
        setBackgroundResource(resId)
        return this
    }

    fun bgTintList(colorStateList: ColorStateList): GSpinner {
        ViewCompat.setBackgroundTintList(this, colorStateList)
        return this
    }

    fun enabled(enabled: Boolean): GSpinner {
        isEnabled = enabled
        return this
    }

    fun onItemSelected(listener: AdapterView.OnItemSelectedListener): GSpinner {
        onItemSelectedListener = listener
        return this
    }
}
