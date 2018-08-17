package com.gani.lib.ui.layout

import android.view.View
import com.gani.lib.ui.view.IView

interface ILayout: IView {
    fun append(child: View): ILayout
}
