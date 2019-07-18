package com.glib.core.ui.layout

import android.view.View
import com.glib.core.ui.view.IView

interface ILayout: IView {
    fun append(child: View): ILayout
}
