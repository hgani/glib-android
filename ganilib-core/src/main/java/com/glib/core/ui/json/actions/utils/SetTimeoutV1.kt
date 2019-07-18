package com.glib.core.ui.json.actions.utils

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class SetTimeoutV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
//        val message = spec["message"].string
//        if (message == null) {
//            return false
//        }
//        screen.launch.alert(message)
        return true
    }
}
