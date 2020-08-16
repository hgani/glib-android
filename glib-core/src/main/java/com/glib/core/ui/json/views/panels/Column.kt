package com.glib.core.ui.json.views.panels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GHorizontalPanel
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GWeightable

class Column(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GVerticalPanel(context)

    override fun initView(): View {
        JsonUi.initVerticalPanel(panel, spec, screen, fragment)
        return panel
    }
}
