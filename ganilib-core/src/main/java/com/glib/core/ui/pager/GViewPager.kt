package com.glib.core.ui.pager

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import com.glib.core.ui.Ui
import com.glib.core.ui.view.ViewHelper
import java.util.*

class GViewPager : ViewPager {
    private var helper = ViewHelper(this)

    private var timer: Timer? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun width(width: Int?): GViewPager {
        helper.width(width)
        return this
    }

    fun height(height: Int?): GViewPager {
        helper.height(height)
        return this
    }

    fun bgColor(res: Int): GViewPager {
        helper.bgColor(res)
        return this
    }

    fun adapter(adapter: PagerAdapter): GViewPager {
        super.setAdapter(adapter)
        return this
    }

    //  @Override
    //  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //
    //    int height = 0;
    //
    //    for (int i = 0; i < getChildCount(); i++) {
    //      View child = getChildAt(i);
    //
    //      child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    //
    //      int h = child.getMeasuredHeight();
    //
    //      if (h > height) height = h;
    //    }
    //
    //    heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
    //
    //    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    //  }

    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): GViewPager {
        helper.padding(left, top, right, bottom)
        return this
    }

    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): GViewPager {
        helper.margin(left, top, right, bottom)
        return this
    }

    // See http://stackoverflow.com/questions/17610085/how-to-switch-automatically-between-viewpager-pages
    fun autoScroll(millis: Int): GViewPager {
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

                this@GViewPager.currentItem = page
            }
        }
    }
}


