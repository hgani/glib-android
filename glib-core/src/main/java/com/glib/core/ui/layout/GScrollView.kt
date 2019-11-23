package com.glib.core.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView
import com.glib.core.ui.view.IView
import com.glib.core.ui.view.ViewHelper

open class GScrollView<T : GScrollView<T>> : ScrollView, IView {
    private val helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        // Nothing to do
    }

    override fun width(width: Int?): T {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): T {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): T {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): T {
        setBackgroundColor(color)
        return self()
    }

    fun bgColor(code: String): T {
        helper.bgColor(code)
        return self()
    }

//    // Can't be used before setting bgColor()
//    fun border(color: Int, thickness: Int, cornerRadius: Int): T {
//        val borderColorDrawable = GradientDrawable()
//        borderColorDrawable.cornerRadius = Length.dpToPx(cornerRadius).toFloat()
//        borderColorDrawable.setStroke(Length.dpToPx(thickness), color)
//
//        val backgroundColorDrawable = background
//        val drawables = arrayOf(borderColorDrawable, backgroundColorDrawable)
//
//        val layerDrawable = LayerDrawable(drawables)
//        layerDrawable.setLayerInset(1, thickness, thickness, thickness, thickness)
//        background = layerDrawable
//        return self()
//    }

//    fun gravity(alignment: Int): T {
//        gravity = alignment
//        return self()
//    }

    private fun self(): T {
        return this as T
    }

    open fun append(child: View): T {
        addView(child)
        return self()
    }

//    fun alignParentRight(): T {
//        //    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
//        //    params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        helper.alignParentRight()
//        return self()
//    }
}
