package com.gani.lib.ui.json.views

import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GButton

class ButtonV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val button = GButton(context)

    override fun initView(): View {
        spec["text"].string?.let { button.text(it) }

        button.onClick {
            JsonAction.execute(spec["onClick"], screen, it)
        }

        return button
    }
}
