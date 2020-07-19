package com.glib.core.ui.json.views.fields

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GButton

class SubmitV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val button = GButton(context)

    override fun showProgress() {
        button.isEnabled = false
    }

    override fun hideProgress() {
        button.isEnabled = true
    }

    override fun initView(): View {
        spec["text"].string?.let { button.text(it) }

        button.onClick {
            val submitSpec = GJsonObject.Default(hashMapOf("action" to "forms/submit-v1"))
            JsonAction.execute(submitSpec, screen, it, this)
        }

        return button
    }
}
