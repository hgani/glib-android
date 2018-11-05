package com.gani.map

import android.content.Context
import android.util.AttributeSet
import com.gani.lib.ui.view.IView
import com.gani.lib.ui.view.ViewHelper
import com.google.android.gms.maps.MapView

class GMapView : MapView, IView {
    private val helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun self(): GMapView {
        return this
    }

    override fun width(width: Int?): GMapView {
        helper.width(width)
        return self()
    }

    override fun height(height: Int?): GMapView {
        helper.height(height)
        return self()
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GMapView {
        helper.padding(l, t, r, b)
        return self()
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GMapView {
        helper.margin(l, t, r, b)
        return self()
    }

    override fun bgColor(color: Int): GMapView {
        helper.bgColor(color)
        return self()
    }
}
