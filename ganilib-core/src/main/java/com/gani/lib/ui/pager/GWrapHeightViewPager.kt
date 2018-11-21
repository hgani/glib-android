package com.gani.lib.ui.pager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.gani.lib.ui.Ui
import com.gani.lib.ui.view.ViewHelper
import java.util.*

class GWrapHeightViewPager : ViewPager {
    private var helper: ViewHelper? = null

    private var timer: Timer? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.helper = ViewHelper(this)
    }

    fun adapter(adapter: PagerAdapter): GWrapHeightViewPager {
        super.setAdapter(adapter)
        return this
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

            val h = child.measuredHeight

            if (h > height) height = h
        }

        heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): GWrapHeightViewPager {
        helper!!.padding(left, top, right, bottom)
        return this
    }

    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): GWrapHeightViewPager {
        helper!!.margin(left, top, right, bottom)
        return this
    }

    // See http://stackoverflow.com/questions/17610085/how-to-switch-automatically-between-viewpager-pages
    fun autoScroll(millis: Int): GWrapHeightViewPager {
        if (timer == null) {
            timer = Timer() // At this line a new Thread will be created
            timer!!.scheduleAtFixedRate(RemindTask(), 0, millis.toLong())
        }
        return this
    }

    private inner class RemindTask : TimerTask() {
        private var page = 0

        override fun run() {
            // As the TimerTask run on a seprate thread from UI thread we have
            // to call runOnUiThread to do work on UI thread.
            Ui.exec {
                page++

                if (page >= adapter!!.count) {
                    page = 0
                    //            timer.cancel();
                    //            // Showing a toast for just testing purpose
                    //            Toast.makeText(getApplicationContext(), "Timer stoped",
                    //                Toast.LENGTH_LONG).show();
                }

                this@GWrapHeightViewPager.currentItem = page
            }
        }
    }
}


