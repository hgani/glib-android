package com.gani.lib.ui.json.views

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GTextView

class LabelV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val label = GTextView(context)

    override fun initView(): View {
        spec["text"].string?.let { label.text(it) }

        label.onClick {
            JsonAction.executeAll(spec["onClick"], screen, it)
        }

        return label
    }
}
