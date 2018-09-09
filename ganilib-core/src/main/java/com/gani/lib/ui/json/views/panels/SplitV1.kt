package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.panel.GSplitPanel
import com.gani.lib.ui.view.GView

class SplitV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GSplitPanel(context)

    override fun initView(): View {
        val content = spec["content"]
        panel.withViews(createSubview(content["left"]), createSubview(content["center"]), createSubview(content["right"]))
        return panel
    }

    private fun createSubview(subviewSpec: GJson) : View {
        if (subviewSpec.isNull()) {
            return GView(context)
        }
        return JsonView.create(subviewSpec, screen, fragment)?.createView() ?: GView(context)
    }
}
