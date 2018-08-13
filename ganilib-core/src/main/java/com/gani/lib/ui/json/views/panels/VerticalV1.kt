package com.gani.lib.ui.json.views.panels

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import com.gani.lib.json.GJsonObject
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.layout.GLinearLayout

class VerticalV1: JsonView {
    private val panel: GLinearLayout

    constructor(spec: GJsonObject<*, *>, screen: GActivity): this(GLinearLayout(screen).vertical(), spec, screen)

    constructor(panel: GLinearLayout, spec: GJsonObject<*, *>, screen: GActivity): super(spec, screen) {
        this.panel = panel

        // TODO
        panel.bgColor(Color.RED).width(ViewGroup.LayoutParams.MATCH_PARENT).height(ViewGroup.LayoutParams.MATCH_PARENT)
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
