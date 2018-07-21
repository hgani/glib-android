package com.gani.lib.ui.layout

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout

class GRelativeLayoutParams<T : View>(private val view: T) : RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT) {

    fun alignRight(): GRelativeLayoutParams<T> {
        addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        return this
    }

    fun centerVertical(): GRelativeLayoutParams<T> {
        addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
        return this
    }

    fun end(): T {
        return view
    }
}
