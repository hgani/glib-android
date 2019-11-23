package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.glib.core.ui.layout.GRelativeLayout
import com.glib.core.ui.view.IView

class GRelativePanel: GRelativeLayout<GRelativePanel>, IView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
