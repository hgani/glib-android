package com.glib.core.ui.json.actions.forms

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.views.panels.Form

class Submit(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        execute(targetView, targetController)
        return true
    }

    companion object {
        fun execute(view: View?, controller: JsonView?) {
            var ancestor = view?.parent
            while (ancestor != null && !(ancestor is Form.FormPanel)) {
                ancestor = ancestor.parent
            }
            (ancestor as? Form.FormPanel)?.submit(controller)
        }
    }
}
