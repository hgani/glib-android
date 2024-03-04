package com.glib.core.ui.json.actions.windows

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class Open(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }
        screen.startActivityForResult(JsonUiScreen.intent(url, false), 0)
        return true
    }
}
