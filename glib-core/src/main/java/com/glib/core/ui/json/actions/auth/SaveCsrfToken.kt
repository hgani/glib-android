package com.glib.core.ui.json.actions.auth

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.utils.GAuth

class SaveCsrfToken(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        GAuth.csrfToken = spec["token"].string
        execute(spec["onSave"], screen, this)
        return true
    }
}
