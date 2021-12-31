package com.glib.core.ui.json.views

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.view.GTextView

class H6(spec: GJson, screen: GActivity, fragment: GFragment): AbstractText(spec, screen, fragment) {
    override fun initView(): GTextView {
        val view = super.initView().textSize(12f).bold()
        applyStyleClass("h6")
        return view
    }
}
