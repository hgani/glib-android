package com.glib.core.ui.json.views.panels

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.actions.windows.JsonUiStyling
import com.glib.core.ui.panel.GHorizontalPanel
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GWeightable

class Horizontal(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GHorizontalPanel(context)

    override fun initView(): View {
        val subviews = (spec["subviews"].array ?: spec["childViews"].arrayValue).mapNotNull { subviewSpec ->
            create(subviewSpec, screen, fragment)?.createView()
        }

        spec["onClick"].presence?.let { spec ->
            panel.onClick {
                JsonAction.execute(spec, screen, panel, this)
            }
        }

        // Doesn't seem to work
//        when (spec["align"].stringValue) {
//            "middle" -> {
//                GLog.t(javaClass, "ALIGN1: ${spec["align"].stringValue}")
//                panel.gravity(Gravity.CENTER_VERTICAL)
//            }
//            "bottom" -> {
//                GLog.t(javaClass, "ALIGN2: ${spec["align"].stringValue}")
//                panel.bgColor(Color.RED).gravity(Gravity.BOTTOM).gravity(Gravity.LEFT)
//            }
//            else -> {
//                GLog.t(javaClass, "ALIGN3: ${spec["align"].stringValue}")
//                panel.bgColor(Color.RED).gravity(Gravity.TOP).gravity(Gravity.RIGHT)
//            }
//        }

        when (spec["distribution"].stringValue) {
            "fillEqually" -> {
                subviews.forEach {
                    (it as? GWeightable)?.weight(1f)
                    panel.addView(it)
                }
                panel.width(ViewGroup.LayoutParams.MATCH_PARENT)
            }
            "spaceEqually" -> {
                subviews.forEach {
                    panel.addView(GVerticalPanel(screen).gravity(Gravity.CENTER_HORIZONTAL).weight(1f).append(it))
                }
                panel.width(ViewGroup.LayoutParams.MATCH_PARENT)
            }
            else -> {
                subviews.forEach {
                    panel.addView(it)
                }
            }
        }
        return panel
    }

    override fun applyStyleClass(styleClass: String) {
        JsonUiStyling.horizontalPanels[styleClass]?.let {
            it.decorate(panel)
        }
    }
}
