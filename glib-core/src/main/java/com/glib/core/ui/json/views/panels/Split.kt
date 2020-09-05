package com.glib.core.ui.json.views.panels

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GSplitPanel
import com.glib.core.ui.view.GView

class Split(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GSplitPanel(context)

    override fun initView(): View {
//        val content = spec["content"]
        panel.withViews(createSubview(spec["left"]), createSubview(spec["center"]), createSubview(spec["right"]))
        return panel
    }

    private fun createSubview(subviewSpec: GJson) : View {
        if (subviewSpec.isNull()) {
            return GView(context).width(0)
        }

//        return create(subviewSpec, screen, fragment)?.createView() ?: GView(context)
        return Default(subviewSpec, screen, fragment).createView()
    }
}
