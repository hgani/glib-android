package com.gani.lib.ui.panel

import android.content.Context
import android.util.AttributeSet
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.view.IView

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
