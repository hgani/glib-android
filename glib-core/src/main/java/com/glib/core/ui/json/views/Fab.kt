package com.glib.core.ui.json.views

import androidx.core.view.GravityCompat
import android.view.Gravity
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GFloatingActionButton
import com.glib.core.ui.view.GView

class Fab(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val button = GView(context)
    private val fab = GFloatingActionButton(context).attachTo(fragment)

    override fun showProgress() {
        fab.isEnabled = false
    }

    override fun hideProgress() {
        fab.isEnabled = true
    }

    override fun initView(): View {
        fab.coordinator {
            it.gravity(Gravity.BOTTOM, GravityCompat.END)
            it.rightMargin = 10
            it.bottomMargin = 18
        }
        fab.source(JsonUi.iconDrawable(spec["icon"]))
        fab.onClick {
            JsonAction.execute(spec["onClick"], screen, button, this)
        }

        return button
    }
}
