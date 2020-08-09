package com.glib.core.ui.json.actions.dialogs

import com.glib.core.dialog.GRestDialog
import com.glib.core.http.GRestResponse
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi

class Open(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }

        val intent = GRestDialog.intentForUrl(url, Rest.GET, null, false, object : GRestDialog.Callback {
            override fun onRestResponse(fragment: GFragment, response: GRestResponse) {
                JsonUi.parseScreenContent(response.result, fragment)
            }
        })
        screen.startActivity(intent)

        return true
    }
}
