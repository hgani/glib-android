package com.glib.core.ui.json.actions.windows

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class ReloadV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        (screen.mainFragment as? JsonUiFragment)?.let {
            it.reload(spec["url"].string ?: it.path, false)
        }
        return true
    }
}
