package com.glib.core.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.glib.core.ui.layout.GLinearLayout
import com.glib.core.ui.view.IView

class GVerticalPanel: GLinearLayout<GVerticalPanel>, IView {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        vertical()
    }
}
