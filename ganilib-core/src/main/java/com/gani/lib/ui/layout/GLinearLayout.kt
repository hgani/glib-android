package com.gani.lib.ui.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gani.lib.ui.view.ViewHelper

//import android.content.Context
//import android.graphics.drawable.Drawable
//import android.support.v4.view.ViewCompat
//import android.util.AttributeSet
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//import com.gani.lib.ui.view.ViewHelper
//
//class GLinearLayout : AbstractLinearLayout<GLinearLayout> {
//    constructor(context: Context) : super(context) {}
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
//}
//
//package com.gani.lib.ui.layout
//
//import android.content.Context
//import android.graphics.drawable.Drawable
//import android.support.v4.view.ViewCompat
//import android.util.AttributeSet
//import android.view.View
//import android.view.ViewGroup
//import android.widget.LinearLayout
//
//import com.gani.lib.ui.view.ViewHelper

open class GLinearLayout : LinearLayout {
    private var helper = ViewHelper(this)

    constructor(context: Context) : super(context)
//    {
//
//        init()
//    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
//    {
//        init()
//    }

//    private fun init() {
//        this.helper = ViewHelper(this)
//    }

    private fun self(): GLinearLayout {
        return this
    }

    fun width(width: Int?): GLinearLayout {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): GLinearLayout {
        helper.height(height)
        return self()
    }

    fun vertical(): GLinearLayout {
        this.orientation = LinearLayout.VERTICAL
        return self()
    }

    fun horizontal(): GLinearLayout {
        this.orientation = LinearLayout.HORIZONTAL
        return self()
    }

    fun paddings(l: Int?, t: Int?, r: Int?, b: Int?): GLinearLayout {
        helper.padding(l, t, r, b)
        return self()
    }

    fun margins(l: Int?, t: Int?, r: Int?, b: Int?): GLinearLayout {
        helper.margin(l, t, r, b)
        return self()
    }

    fun gravity(gravity: Int): GLinearLayout {
        setGravity(gravity)
        return self()
    }

    fun bgColor(color: Int): GLinearLayout {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): GLinearLayout {
        helper.bgColor(code)
        return self()
    }

    fun bg(drawable: Drawable): GLinearLayout {
//        val sdk = android.os.Build.VERSION.SDK_INT
//        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            setBackgroundDrawable(drawable)
//        } else {
//            setBackground(drawable)
//        }
//
//        // Not sure why this doesn't work
//        //    ViewCompat.setBackground(this, drawable);

        setBackground(drawable)
        return self()
    }

    fun setWeightOf(child: View, weight: Int) {
        var params: LinearLayout.LayoutParams? = child.layoutParams as LinearLayout.LayoutParams
        if (params == null) {
            params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        params.width = 0
        params.weight = weight.toFloat()
        child.layoutParams = params
    }

    fun rtl(): GLinearLayout {
        ViewCompat.setLayoutDirection(this, ViewCompat.LAYOUT_DIRECTION_RTL)
        return self()
    }

    fun onClick(listener: View.OnClickListener): GLinearLayout {
        setOnClickListener(listener)
        return self()
    }

    fun append(child: View): GLinearLayout {
        addView(child)
        return self()
    }
}
