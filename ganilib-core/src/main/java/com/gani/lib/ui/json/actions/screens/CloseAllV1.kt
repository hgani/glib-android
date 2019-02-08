package com.gani.lib.ui.json.actions.screens

import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.actions.windows.JsonUiScreen

class CloseAllV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        screen.startActivity(JsonUiScreen.intent(spec["onClose"]))
        return true
    }
}