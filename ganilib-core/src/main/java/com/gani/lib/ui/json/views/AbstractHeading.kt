package com.gani.lib.ui.json.views

import android.view.ViewGroup
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GTextView

open class AbstractHeading(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val label = GTextView(context).width(ViewGroup.LayoutParams.MATCH_PARENT)

    override fun initView(): GTextView {
        spec["text"].string?.let { label.text(it) }

        label.onClick {
            JsonAction.execute(spec["onClick"], screen, it)
        }

        return label
    }
}
