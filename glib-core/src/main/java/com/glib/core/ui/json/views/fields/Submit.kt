package com.glib.core.ui.json.views.fields

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.actions.forms.Submit
import com.glib.core.ui.view.GButton
import com.glib.core.ui.view.IView

class Submit(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val button = GButton(context)

    override fun showProgress() {
        button.isEnabled = false
    }

    override fun hideProgress() {
        button.isEnabled = true
    }

    override fun initView(): View {
        initColor()

        spec["text"].string?.let { button.text(it) }

        button.onClick {
            Submit.execute(it, this)

//            val submitSpec = GJsonObject.Default(hashMapOf("action" to "forms/submit-v1"))
//            JsonAction.execute(submitSpec, screen, it, this)
        }

        return button
    }

    override fun initBackgroundColor(view: IView) {
        ifColor(spec["backgroundColor"].string) {
            button.bgTint(it)
        }
    }

    private fun initColor() {
        ifColor(spec["color"].string) {
            button.color(it)
        }
    }
}
