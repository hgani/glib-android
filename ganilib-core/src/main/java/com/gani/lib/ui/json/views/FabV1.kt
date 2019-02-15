package com.gani.lib.ui.json.views

import android.support.v4.view.GravityCompat
import android.view.Gravity
import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonUi
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.view.GFloatingActionButton
import com.gani.lib.ui.view.GView

class FabV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
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
