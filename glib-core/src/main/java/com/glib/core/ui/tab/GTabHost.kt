package com.glib.core.ui.tab

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTabHost
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.model.GBundle
import com.glib.core.screen.GFragment
import com.glib.core.ui.panel.GFramePanel
import com.glib.core.ui.panel.GHorizontalPanel
import com.glib.core.ui.panel.GHorizontalScrollPanel
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.ViewHelper
import com.glib.core.utils.Res

open class GTabHost : FragmentTabHost {
    private val helper: ViewHelper = ViewHelper(this)

    private var fragmentManager: FragmentManager? = null

    val currentTabFragment: GFragment
        get() = fragmentManager?.findFragmentByTag(currentTabTag) as GFragment

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

//    private var tabWidget: GTabWidget? = null

    private fun widgetView(tabWidget: GTabWidget, scroll: Boolean): View {
//        this.tabWidget = tabWidget

        if (scroll) {
            val scroller = GHorizontalScrollPanel(context)
            scroller.isHorizontalScrollBarEnabled = false
//        scroller.isFillViewport = true
//            scroller.layoutAnimation = LayoutAnimationController(AnimationUtils.loadAnimation(Res.context, R.anim.slide_right))
            return scroller.append(tabWidget)
        }
        return tabWidget
    }

    private fun initView(scroll: Boolean, tabPanel: GHorizontalPanel) {
        val container = GVerticalPanel(context).width(ViewGroup.LayoutParams.MATCH_PARENT).height(ViewGroup.LayoutParams.MATCH_PARENT)
        container.addView(tabPanel
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .append(widgetView(GTabWidget(context).width(ViewGroup.LayoutParams.MATCH_PARENT).id(android.R.id.tabs), scroll))
        )
        container.addView(GFramePanel(context)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .height(ViewGroup.LayoutParams.MATCH_PARENT).id(android.R.id.tabcontent)
        )
        addView(container)
    }

    private fun self(): GTabHost {
        return this
    }

    fun setup(fragmentManager: FragmentManager?, scroll: Boolean, tabPanel: GHorizontalPanel): GTabHost {
        this.fragmentManager = fragmentManager!!

        initView(scroll, tabPanel)
        super.setup(context, fragmentManager, android.R.id.tabcontent)
        return self()
    }

    fun addTab(label: String, fragmentClass: Class<*>, args: GJson, tag: String? = null) {
        super.addTab(newTabSpec(tag ?: label).setIndicator(label), fragmentClass, GBundle().set(args).native)
    }

    fun addTab(label: String, fragmentClass: Class<*>, args: GBundle = GBundle(), tag: String? = null) {
        super.addTab(newTabSpec(tag ?: label).setIndicator(label), fragmentClass, args.native)
    }

    private fun enableMoveViewportToFocusOnSelectedTab() {
        // https://stackoverflow.com/questions/6131218/android-programmatic-scrolling-of-tabwidget
        (0..tabWidget.childCount - 1).forEach { tabWidget.getChildAt(it).isFocusableInTouchMode = true }
    }

    private fun disableMoveViewportToFocusOnSelectedTab() {
        (0..tabWidget.childCount - 1).forEach { tabWidget.getChildAt(it).isFocusableInTouchMode = false }
    }

    fun selectTab(tag: String) {
        // Need to re-disable otherwise for some reason the user sometimes needs to click the tab twice to activate it.
        enableMoveViewportToFocusOnSelectedTab()
        setCurrentTabByTag(tag)
        disableMoveViewportToFocusOnSelectedTab()
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
