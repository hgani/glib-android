package com.gani.map

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.view.IView
import com.gani.lib.ui.view.ViewHelper
import com.google.android.gms.maps.MapView

// NOTE: To use this, set API key in manifest:
// <meta-data
//  android:name="com.google.android.geo.API_KEY"
//  android:value="" />
class GMapView : MapView, IView, GFragment.LifecycleListener {
    private val helper: ViewHelper = ViewHelper(this)
    private var attachedFragment: GFragment? = null

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



    ///// Fragment Lifecycle Integration /////

    fun attachTo(fragment: GFragment): GMapView {
        fragment.addLifecycleListener(this)
        attachedFragment = fragment
        onCreate(null)
        return self()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (attachedFragment == null) {
            throw IllegalStateException("Make sure to call attachTo() first")
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        attachedFragment?.removeLifecycleListener(this)
    }

    // See http://www.zoftino.com/android-mapview-tutorial
    private val MAP_VIEW_BUNDLE_KEY = "__GMapViewBundleKey"

    override fun onFragmentCreate(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        onCreate(mapViewBundle)
    }

    override fun onFragmentSaveInstanceState(outState: Bundle) {
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }

        onSaveInstanceState(mapViewBundle)
    }

    override fun onFragmentResume() {
        onResume()
    }

    override fun onFragmentStart() {
        onStart()
    }

    override fun onFragmentStop() {
        onStop()
    }

    override fun onFragmentPause() {
        onPause()
    }

    override fun onFragmentDestroy() {
        onDestroy()
    }

    override fun onFragmentLowMemory() {
        onLowMemory()
    }

    /////
}
