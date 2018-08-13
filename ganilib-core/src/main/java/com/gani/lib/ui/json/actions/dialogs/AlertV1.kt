package com.gani.lib.ui.json.actions.dialogs

import com.gani.lib.json.GJsonObject
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction

class AlertV1(spec: GJsonObject<*, *>, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val message = spec["message"].string
        if (message == null) {
            return false
        }
        screen.launch.alert(message)
        return true
    }
}
