package com.glib.core.ui.json.actions.windows

import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class CloseV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    companion object {
        val RESULT_KEY_ON_AFTER_CLOSE = "onAfterClose"
    }

    override fun silentExecute(): Boolean {
        screen.finish(RESULT_KEY_ON_AFTER_CLOSE, spec["onClose"])
        return true
    }
}
