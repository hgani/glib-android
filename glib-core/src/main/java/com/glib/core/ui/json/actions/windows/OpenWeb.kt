package com.glib.core.ui.json.actions.windows

import com.glib.core.http.UrlUtils
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction

class OpenWeb(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string ?: return false
        screen.launch.url(UrlUtils.jsonToHtml(url))
        return true
    }
}
