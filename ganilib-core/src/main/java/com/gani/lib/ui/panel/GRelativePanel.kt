package com.gani.lib.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.gani.lib.ui.layout.GRelativeLayout
import com.gani.lib.ui.view.IView

class GRelativePanel: GRelativeLayout<GRelativePanel>, IView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
