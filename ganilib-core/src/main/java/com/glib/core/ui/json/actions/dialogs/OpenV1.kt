package com.glib.core.ui.json.actions.dialogs

import com.glib.core.dialog.GRestDialogProgress
import com.glib.core.http.GRestResponse
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import java.lang.RuntimeException

class OpenV1(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {

        val url = spec["url"].string
//        if (true) {
//            throw RuntimeException("UR: ${url}")
//        }
        if (url == null) {
            return false
        }
//        screen.launch.alert(message, null, {
//            JsonAction.execute(spec["onClose"], screen, null, null)
//        })

        val intent = GRestDialogProgress.intentForUrl(url, Rest.GET, null, object : GRestDialogProgress.Callback {
            override fun onRestResponse(fragment: GFragment, response: GRestResponse) {
                JsonUi.parseScreenContent(response.result, fragment)
            }
        })
        screen.startActivity(intent)

        return true
    }
}
