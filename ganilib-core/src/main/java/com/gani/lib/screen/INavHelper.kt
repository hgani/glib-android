package com.gani.lib.screen

import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.ViewGroup

abstract class INavHelper {
//    abstract val toolbar: Toolbar?
    open val toolbar: Toolbar?
        get() = null  // Not applicable to dialog

    abstract val layout: ViewGroup

    abstract fun setBody(resId: Int)

    internal open fun initNavigation(topNavigation: Boolean, actionBar: ActionBar) {
        // Do nothing by default
    }

    open fun openLeftDrawer() {
        // Do nothing by default
    }

    open fun openRightDrawer() {
        // Do nothing by default
    }

    open fun showHomeIcon() {
        // Do nothing by default
    }

    internal open fun handleHomeClick(): Boolean {
        // Do nothing by default
        return false
    }
}
