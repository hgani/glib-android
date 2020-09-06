package com.glib.core.ui.layout

import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

class GRelativeLayoutParams: RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    fun alignTop() {
        addRule(RelativeLayout.ALIGN_PARENT_TOP)
    }

    fun alignBottom() {
        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    }

    fun alignLeft() {
        addRule(RelativeLayout.ALIGN_PARENT_LEFT)
    }

    fun alignRight() {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    }

    fun alignMiddle() {
        addRule(RelativeLayout.CENTER_VERTICAL)
    }

    fun alignCenter() {
        addRule(RelativeLayout.CENTER_HORIZONTAL)
    }

    fun above(view: View) {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        addRule(RelativeLayout.ABOVE, view.id)
    }

    fun below(view: View) {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        addRule(RelativeLayout.BELOW, view.id)
    }

    fun leftOf(view: View) {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        addRule(RelativeLayout.LEFT_OF, view.id)
    }

    fun rightOf(view: View) {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        addRule(RelativeLayout.RIGHT_OF, view.id)
    }

    fun centerVertical() {
        addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
    }

    fun centerHorizontal() {
        addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
    }

    fun alignParentStart() {
        addRule(RelativeLayout.ALIGN_PARENT_START)
    }

    fun alignParentEnd() {
        addRule(RelativeLayout.ALIGN_PARENT_END)
    }

    fun alignParentTop() {
        addRule(RelativeLayout.ALIGN_PARENT_TOP)
    }

    fun alignParentBottom() {
        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
    }

    fun bottomTo(view: View) {
        if (view.id == View.NO_ID) {
            view.id = View.generateViewId()
        }
        addRule(RelativeLayout.ALIGN_BOTTOM, view.id)
    }

    fun width(width: Int) {
        this.width = width
    }

    fun height(height: Int) {
        this.height = height
    }
}
