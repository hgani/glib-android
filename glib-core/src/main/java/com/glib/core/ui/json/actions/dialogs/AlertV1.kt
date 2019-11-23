package com.glib.core.ui.json.actions.dialogs

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class AlertV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val message = spec["message"].string
        if (message == null) {
            return false
        }
        screen.launch.alert(message, null, {
            JsonAction.execute(spec["onClose"], screen, null, null)
        })
        return true
    }
}
