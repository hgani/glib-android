package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.panel.GVerticalPanel

class VerticalV1: JsonView {
    private val panel: GVerticalPanel

    constructor(spec: GJson, screen: GActivity, fragment: GFragment): this(GVerticalPanel(screen), spec, screen, fragment)

    constructor(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment): super(spec, screen, fragment) {
        this.panel = panel
    }

    override fun initView(): View {
        spec["subviews"].arrayValue.forEach { subviewSpec ->
            JsonView.create(subviewSpec, screen, fragment)?.let {jsonView ->
                panel.addView(jsonView.createView())
            }
        }
        return panel
    }
}
