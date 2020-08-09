package com.glib.core.ui.json.actions.forms

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.views.panels.Form

class Submit(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        var ancestor = targetView?.parent
        while (ancestor != null && !(ancestor is Form.FormPanel)) {
            ancestor = ancestor.parent
        }
        (ancestor as? Form.FormPanel)?.submit(targetController)
        return true
    }
}
