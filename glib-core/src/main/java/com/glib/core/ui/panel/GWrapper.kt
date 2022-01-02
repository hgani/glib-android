package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.glib.core.ui.layout.GRelativeLayout
import com.glib.core.ui.view.IView

open class GWrapper: GRelativeLayout<GWrapper>, IView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
