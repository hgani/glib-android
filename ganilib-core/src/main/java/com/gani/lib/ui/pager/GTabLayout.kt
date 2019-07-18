package com.gani.lib.ui.pager

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.gani.lib.ui.layout.GRelativeLayoutParams
import com.gani.lib.ui.view.ViewHelper
import com.google.android.material.tabs.TabLayout


class GTabLayout : TabLayout {
    private var helper = ViewHelper(this)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun width(width: Int?): GTabLayout {
        helper.width(width)
        return this
    }

    fun height(height: Int?): GTabLayout {
        helper.height(height)
        return this
    }

    fun bgColor(res: Int): GTabLayout {
        helper.bgColor(res)
        return this
    }

    fun padding(left: Int?, top: Int?, right: Int?, bottom: Int?): GTabLayout {
        helper.padding(left, top, right, bottom)
        return this
    }

    fun margin(left: Int?, top: Int?, right: Int?, bottom: Int?): GTabLayout {
        helper.margin(left, top, right, bottom)
        return this
    }

    fun setupWith(pager: ViewPager): GTabLayout {
        setupWithViewPager(pager)
        return this
    }

    fun tabGravity(gravity: Int): GTabLayout {
        this.tabGravity = gravity
        return this
    }

    fun relative(addRules: (GRelativeLayoutParams) -> (Unit)): GTabLayout {
        helper.relative(addRules)
        return this
    }
}


