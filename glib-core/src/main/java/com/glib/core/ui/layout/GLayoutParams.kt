package com.glib.core.ui.layout

import android.widget.LinearLayout
import com.glib.core.ui.style.Length.dpToPx

class GLayoutParams<T : GLayoutParams<T>> : LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) {
    private fun self(): T {
        return this as T
    }

    fun width(width: Int): T {
        this.width = dpToPx(width)
        return self()
    }

    fun height(height: Int): T {
        this.height = dpToPx(height)
        return self()
    }

    fun margins(left: Int, top: Int, right: Int, bottom: Int): T {
        super.setMargins(dpToPx(left), dpToPx(top), dpToPx(right), dpToPx(bottom))
        return self()
    }
}
