package com.glib.core.ui.json.views

import android.graphics.Color
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GButton
import com.glib.core.ui.view.IView

class Button(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
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
            JsonAction.execute(spec["onClick"], screen, it, this)
        }

        if (styleClasses().contains("link")) {
            button.background = null
            button.color(Color.BLUE)
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
