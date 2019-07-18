package com.glib.core.ui.json.views.panels

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.view.GWeightable

class VerticalV1: JsonView {
    private val panel: GVerticalPanel

    constructor(spec: GJson, screen: GActivity, fragment: GFragment): this(GVerticalPanel(screen), spec, screen, fragment)

    constructor(panel: GVerticalPanel, spec: GJson, screen: GActivity, fragment: GFragment): super(spec, screen, fragment) {
        this.panel = panel
    }

    override fun initView(): View {
//        spec["subviews"].arrayValue.forEach { subviewSpec ->
//            JsonView.create(subviewSpec, screen, fragment)?.let {jsonView ->
//                panel.addView(jsonView.createView())
//            }
//        }

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
                    panel.addView(GVerticalPanel(screen).gravity(Gravity.CENTER_VERTICAL).weight(1f).append(it))
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
