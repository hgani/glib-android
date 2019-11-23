package com.glib.core.ui.json.views

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.view.GTextView

class H4V1(spec: GJson, screen: GActivity, fragment: GFragment): AbstractHeading(spec, screen, fragment) {
    override fun initView(): GTextView {
        return super.initView().textSize(14f).bold()
    }
}
