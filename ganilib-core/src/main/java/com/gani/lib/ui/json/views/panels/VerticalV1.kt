package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.panel.GVerticalPanel

class VerticalV1: JsonView {
    private val panel: GVerticalPanel

    constructor(spec: GJson, screen: GActivity): this(GVerticalPanel(screen), spec, screen)

    constructor(panel: GVerticalPanel, spec: GJson, screen: GActivity): super(spec, screen) {
        this.panel = panel
    }

    override fun initView(): View {
        spec["subviews"].arrayValue.forEach { subviewSpec ->
            JsonView.create(subviewSpec, screen)?.let {jsonView ->
                panel.addView(jsonView.createView())
            }
        }
        return panel
    }
}
