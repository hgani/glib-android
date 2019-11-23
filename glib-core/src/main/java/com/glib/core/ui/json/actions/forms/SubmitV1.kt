package com.glib.core.ui.json.actions.forms

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.views.panels.FormV1

class SubmitV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        var ancestor = targetView?.parent
        while (ancestor != null && !(ancestor is FormV1.FormPanel)) {
            ancestor = ancestor.parent
        }
        (ancestor as? FormV1.FormPanel)?.submit(targetController)
        return true
    }
}
