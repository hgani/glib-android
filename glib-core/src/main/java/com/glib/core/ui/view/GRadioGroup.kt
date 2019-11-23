package com.glib.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Checkable
import android.widget.RadioGroup

class GRadioGroup : RadioGroup, IView {
    // https://stackoverflow.com/questions/10461005/how-to-group-radiobutton-from-different-linearlayouts/13273890#13273890
    private val mCheckables = mutableListOf<Checkable>()

    private val helper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

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

    override fun addView(child: View, index: Int, params: android.view.ViewGroup.LayoutParams) {
        super.addView(child, index, params)
        parseChild(child)
    }

    private fun parseChild(child: View) {
        if (child is Checkable) {
            mCheckables.add(child)
            child.setOnClickListener { current ->
                mCheckables.forEach { view ->
                    if (view === current) {
                        view.setChecked(true)
                    } else {
                        view.setChecked(false)
                    }
                }
            }
        } else if (child is ViewGroup) {
            parseChildren(child as ViewGroup)
        }
    }

    private fun parseChildren(child: ViewGroup) {
        for (i in 0 until child.getChildCount()) {
            parseChild(child.getChildAt(i))
        }
    }

    private fun self(): GRadioGroup {
        return this
    }
}
