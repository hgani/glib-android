package com.glib.core.ui.json.views

import android.graphics.Color
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.view.GTextView

class P(spec: GJson, screen: GActivity, fragment: GFragment): AbstractHeading(spec, screen, fragment) {
    override fun initView(): GTextView {
        return super.initView().textSize(12f)
    }
}
