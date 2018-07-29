package com.gani.lib.ui.layout

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout

class GRelativeLayoutParams: RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) {
    fun alignTop() {
        addRule(RelativeLayout.ALIGN_PARENT_TOP)
    }

    fun alignBottom() {
        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
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

    fun alignRight() {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
    }

    fun centerVertical() {
        addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
    }
}
