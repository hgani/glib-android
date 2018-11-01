package com.gani.lib.screen

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.widget.FrameLayout

abstract class IScreenView(activity: GActivity) : FrameLayout(activity) {
    abstract val toolbar: Toolbar?

    internal abstract fun initNavigation(topNavigation: Boolean, actionBar: ActionBar)
    abstract fun setBody(resId: Int)
    abstract fun openLeftDrawer()
    abstract fun openRightDrawer()
}
