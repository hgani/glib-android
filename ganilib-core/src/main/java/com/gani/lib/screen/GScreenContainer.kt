package com.gani.lib.screen

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.gani.lib.ui.panel.GHamburgerPanel
import com.gani.lib.ui.panel.GVerticalPanel


class GScreenContainer: GHamburgerPanel {
    val header = GVerticalPanel(context)
    val content = GVerticalPanel(context)
    val footer = GVerticalPanel(context)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        withViews(header, content, footer)

        // MATCH_PARENT in relative layout
        content.relative {
            it.alignParentStart()
            it.alignParentEnd()
        }
    }
}