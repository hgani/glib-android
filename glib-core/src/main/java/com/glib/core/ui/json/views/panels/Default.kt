package com.glib.core.ui.json.views.panels

import android.view.Gravity
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GVerticalPanel

open class Default: JsonView {
    protected val panel: GVerticalPanel

    constructor(spec: GJson, screen: GActivity, fragment: GFragment): this(GVerticalPanel(screen), spec, screen, fragment)

    constructor(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment): super(spec, screen, fragment) {
        this.panel = panel
    }

    override fun initView(): View {
        when (spec["align"].stringValue) {
            "center" -> {
                panel.gravity(Gravity.CENTER_HORIZONTAL)
            }
            "right" -> {
                panel.gravity(Gravity.RIGHT)
            }
            else -> {
                panel.gravity(Gravity.LEFT)
            }
        }

        val subviews = (spec["subviews"].array ?: spec["childViews"].arrayValue).mapNotNull { subviewSpec ->
            create(subviewSpec, screen, fragment)?.createView()
        }

        subviews.forEach {
            panel.addView(it)
        }

        return panel
    }

    companion object {
        fun initPanel(panel: GVerticalPanel, spec: GJson, activity: GActivity, fragment: GFragment): GVerticalPanel {
            Default(panel, spec, activity, fragment).createView()
            return panel
        }
    }
}
