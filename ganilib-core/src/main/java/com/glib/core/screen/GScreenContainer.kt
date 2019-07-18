package com.glib.core.screen

import android.content.Context
import android.util.AttributeSet
import com.glib.core.ui.panel.GHamburgerPanel
import com.glib.core.ui.panel.GVerticalPanel


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
        header.relative {
            it.alignParentStart()
            it.alignParentEnd()
        }
        content.relative {
            it.alignParentStart()
            it.alignParentEnd()
        }
        footer.relative {
            it.alignParentStart()
            it.alignParentEnd()
        }
    }
}