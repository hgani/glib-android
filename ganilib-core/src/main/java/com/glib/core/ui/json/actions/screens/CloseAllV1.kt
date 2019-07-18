package com.glib.core.ui.json.actions.screens

import com.glib.core.ui.json.actions.windows.JsonUiScreen
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class CloseAllV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        screen.startActivity(JsonUiScreen.intent(spec["onClose"]))
        return true
    }
}