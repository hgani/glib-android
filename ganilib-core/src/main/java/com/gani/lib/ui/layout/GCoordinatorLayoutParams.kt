package com.gani.lib.ui.layout

import android.support.design.widget.CoordinatorLayout
import android.view.ViewGroup

class GCoordinatorLayoutParams: CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT) {
    fun gravity(vararg gravities: Int) {
        var combined = 0
        gravities.forEach {
            combined = combined or it
        }
        this.gravity = combined
    }
}
