package com.gani.lib.screen

import android.content.Context
import android.util.AttributeSet
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.panel.GHamburgerPanel


class GScreenContainer: GHamburgerPanel {
    val header = GLinearLayout(context).vertical()
    val content = GLinearLayout(context).vertical()
    val footer = GLinearLayout(context).vertical()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        withViews(header, content, footer)
    }
}