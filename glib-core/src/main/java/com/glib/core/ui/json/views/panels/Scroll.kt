package com.glib.core.ui.json.views.panels

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GScrollPanel

class Scroll(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GScrollPanel(context)

    override fun initView(): View {
        (spec["subviews"].array ?: spec["childViews"].arrayValue).forEach { subviewSpec ->
            create(subviewSpec, screen, fragment)?.let {jsonView ->
                panel.append(jsonView.createView())
            }
        }
        return panel
    }
}
