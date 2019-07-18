package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.glib.core.ui.layout.GFrameLayout
import com.glib.core.ui.view.IView

class GFramePanel: GFrameLayout<GFramePanel>, IView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
