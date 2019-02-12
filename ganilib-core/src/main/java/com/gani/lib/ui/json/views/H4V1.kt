package com.gani.lib.ui.json.views

import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.view.GTextView

class H4V1(spec: GJson, screen: GActivity, fragment: GFragment): AbstractHeading(spec, screen, fragment) {
    override fun initView(): GTextView {
        return super.initView().textSize(14f).bold()
    }
}
