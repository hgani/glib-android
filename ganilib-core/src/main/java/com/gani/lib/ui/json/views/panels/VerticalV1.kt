package com.gani.lib.ui.json.views.panels

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.layout.GLinearLayout

class VerticalV1: JsonView {
    private val panel: GLinearLayout

    constructor(spec: GJson, screen: GActivity): this(GLinearLayout(screen).vertical(), spec, screen)

    constructor(panel: GLinearLayout, spec: GJson, screen: GActivity): super(spec, screen) {
        this.panel = panel

//        // TODO
//        panel.bgColor(Color.RED)
//                .width(ViewGroup.LayoutParams.MATCH_PARENT)
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
