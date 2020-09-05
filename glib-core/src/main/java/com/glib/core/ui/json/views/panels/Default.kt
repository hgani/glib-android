package com.glib.core.ui.json.views.panels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GWeightable

open class Default: JsonView {
    protected val panel: GVerticalPanel

    constructor(spec: GJson, screen: GActivity, fragment: GFragment): this(GVerticalPanel(screen), spec, screen, fragment)

    constructor(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment): super(spec, screen, fragment) {
        this.panel = panel
    }

    override fun initView(): View {
        GLog.t(javaClass, "ALIGN1 ${spec["align"].stringValue}")
        when (spec["align"].stringValue) {
            "center" -> {
                GLog.t(javaClass, "ALIGN2 ${spec["align"].stringValue}")
                panel.gravity(Gravity.CENTER)
            }
            "right" -> {
                GLog.t(javaClass, "ALIGN3 ${spec["align"].stringValue}")
                panel.gravity(Gravity.RIGHT)
            }
            else -> {
                GLog.t(javaClass, "ALIGN4 ${spec["align"].stringValue}")
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
