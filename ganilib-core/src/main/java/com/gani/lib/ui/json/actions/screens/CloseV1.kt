package com.gani.lib.ui.json.actions.screens

import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.json.JsonAction

class CloseV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        screen.finish()
        return true
    }
}
