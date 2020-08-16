package com.glib.core.ui.json.views

import android.graphics.Color
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GView

class Hr(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val view = GView(context).height(1).bgColor(Color.GRAY)

    override fun initView(): GView {
        return view
    }
}
