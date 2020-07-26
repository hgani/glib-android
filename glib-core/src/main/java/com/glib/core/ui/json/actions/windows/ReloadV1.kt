package com.glib.core.ui.json.actions.windows

import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class ReloadV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        (screen.mainFragment as? JsonUiFragment)?.let {
//            GLog.t(javaClass, "reload2: ${spec["url"].string} - ${it.path}")
            it.reload(spec["url"].string ?: it.path, {
//                GLog.t(javaClass, "R: ${spec["onReload"]}")
                execute(spec["onReload"], screen, this)
            })
        }
        return true
    }
}
