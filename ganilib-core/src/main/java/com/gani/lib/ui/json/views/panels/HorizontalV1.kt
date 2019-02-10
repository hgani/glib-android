package com.gani.lib.ui.json.views.panels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.panel.GHorizontalPanel
import com.gani.lib.ui.panel.GVerticalPanel
import com.gani.lib.ui.view.GWeightable

class HorizontalV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = GHorizontalPanel(context)

    override fun initView(): View {
        val subviews = spec["subviews"].arrayValue.mapNotNull { subviewSpec ->
            JsonView.create(subviewSpec, screen, fragment)?.createView()
        }

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
}
