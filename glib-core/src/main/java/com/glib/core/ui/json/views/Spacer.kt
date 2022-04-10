package com.glib.core.ui.json.views

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GView

class Spacer(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val view = GView(context).width(0).height(0)

    override fun initView(): View {
        return view
    }
}
