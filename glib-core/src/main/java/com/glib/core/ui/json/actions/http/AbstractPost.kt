package com.glib.core.ui.json.actions.http

import com.glib.core.dialog.GRestDialog
import com.glib.core.http.GParams
import com.glib.core.http.GRestCallback
import com.glib.core.http.GRestResponse
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.utils.GAuth

abstract class AbstractPost(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    fun silentExecute(rest: Rest): Boolean {
        val url = spec["url"].string
        if (url == null) {
            return false
        }

        val params = GParams.Default()
        params.put("authenticity_token", GAuth.csrfToken)

        val formData = spec["formData"]
        for (key in formData.keys()) {
            params.put(key, formData[key])
        }

        val callback = GRestCallback.Default(ProgressIndicator.NULL) { response ->
            val result = response.result
            execute(result["onResponse"], screen, this)
        }
        rest.async(url, params, false).execute(callback)

        return true
    }
}
