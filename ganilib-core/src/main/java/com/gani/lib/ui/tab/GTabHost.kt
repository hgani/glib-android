package com.gani.lib.ui.tab

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTabHost
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import com.gani.lib.R
import com.gani.lib.json.GJson
import com.gani.lib.model.GBundle
import com.gani.lib.ui.panel.GFramePanel
import com.gani.lib.ui.panel.GHorizontalScrollPanel
import com.gani.lib.ui.panel.GVerticalPanel
import com.gani.lib.ui.view.ViewHelper
import com.gani.lib.utils.Res

open class GTabHost : FragmentTabHost {
    private val helper: ViewHelper = ViewHelper(this)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private fun widgetView(tabWidget: GTabWidget, scroll: Boolean): View {
        if (scroll) {
            val scroller = GHorizontalScrollPanel(context).width(ViewGroup.LayoutParams.MATCH_PARENT)
            scroller.isHorizontalScrollBarEnabled = false
//        scroller.isFillViewport = true
            scroller.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(Res.context, R.anim.slide_right))
            return scroller.append(tabWidget)
        }
        return tabWidget
    }

    private fun initView(scroll: Boolean, tabWidget: GTabWidget) {
        val container = GVerticalPanel(context).width(ViewGroup.LayoutParams.MATCH_PARENT).height(ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(widgetView(tabWidget.width(ViewGroup.LayoutParams.MATCH_PARENT).id(android.R.id.tabs), scroll))

        container.addView(GFramePanel(context).width(ViewGroup.LayoutParams.MATCH_PARENT).height(ViewGroup.LayoutParams.MATCH_PARENT).id(android.R.id.tabcontent))
        addView(container)
    }

    private fun self(): GTabHost {
        return this
    }

    fun setup(fragmentManager: FragmentManager?, scroll: Boolean, tabWidget: GTabWidget): GTabHost {
        initView(scroll, tabWidget)
        super.setup(context, fragmentManager, android.R.id.tabcontent)
        return self()
    }

    fun addTab(label: String, fragmentClass: Class<*>, args: GJson) {
        super.addTab(newTabSpec(label).setIndicator(label), fragmentClass, GBundle().set(args).native)
    }

    fun addTab(label: String, fragmentClass: Class<*>, args: GBundle = GBundle()) {
        super.addTab(newTabSpec(label).setIndicator(label), fragmentClass, args.native)
    }

    fun width(width: Int?): GTabHost {
        helper.width(width)
        return self()
    }

    fun height(height: Int?): GTabHost {
        helper.height(height)
        return self()
    }

    fun bgColor(code: String): GTabHost {
        bgColor(Res.color(code))
        return self()
    }

    fun bgColor(color: Int): GTabHost {
        setBackgroundColor(color)
        return self()
    }

    fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GTabHost {
        helper.padding(l, t, r, b)
        return self()
    }

    fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GTabHost {
        helper.margin(l, t, r, b)
        return self()
    }
}
