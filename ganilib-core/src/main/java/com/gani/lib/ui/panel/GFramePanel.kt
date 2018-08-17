package com.gani.lib.ui.panel

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gani.lib.ui.layout.GFrameLayout
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.view.IView

class GFramePanel: GFrameLayout<GFramePanel>, IView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
}
