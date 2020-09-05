package com.glib.core.ui.json.views

import android.view.Gravity
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GTextView

open class AbstractText(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val label = GTextView(context).width(ViewGroup.LayoutParams.MATCH_PARENT)

    override fun initView(): GTextView {
        ifColor(spec["color"].string) {
            label.color(it)
        }

        spec["text"].string?.let { label.text(it) }

        spec["textAlign"].string?.let {
            when (it) {
                "center" -> label.gravity(Gravity.CENTER)
                "right" -> label.gravity(Gravity.RIGHT)
                else -> label.gravity(Gravity.LEFT)
            }
        }

        label.onClick {
            JsonAction.execute(spec["onClick"], screen, it, this)
        }

        return label
    }
}
