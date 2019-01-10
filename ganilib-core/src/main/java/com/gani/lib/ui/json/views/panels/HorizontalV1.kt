package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.panel.GHorizontalPanel

class HorizontalV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GHorizontalPanel(context)

    override fun initView(): View {
        spec["subviews"].arrayValue.forEach { subviewSpec ->
            JsonView.create(subviewSpec, screen, fragment)?.let {jsonView ->
                panel.addView(jsonView.createView())
            }
        }
        return panel
    }
}
