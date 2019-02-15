package com.gani.lib.ui.json.actions.forms

import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.views.panels.FormV1

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
