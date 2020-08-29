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

class Patch(spec: GJson, screen: GActivity): AbstractPost(spec, screen) {
    override fun silentExecute(): Boolean {
        return silentExecute(Rest.PATCH)
    }
}
