package com.gani.lib.screen

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.ViewGroup

abstract class INavHelper {
    abstract val toolbar: Toolbar?
    abstract val layout: ViewGroup

    internal abstract fun initNavigation(topNavigation: Boolean, actionBar: ActionBar)
    abstract fun setBody(resId: Int)
    abstract fun openLeftDrawer()
    abstract fun openRightDrawer()
    abstract fun showIcon()
}
