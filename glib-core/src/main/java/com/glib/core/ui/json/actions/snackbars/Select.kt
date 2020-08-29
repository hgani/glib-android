package com.glib.core.ui.json.actions.snackbars

import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.notification.SnackbarUtils
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.glib.core.utils.GAuth
import com.google.android.material.snackbar.Snackbar

class Select(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        val snackbar = SnackbarUtils.standard(screen, spec["message"].stringValue)
        for (buttonSpec in spec["buttons"].arrayValue) {
            // Currently only one action is supported
            snackbar.setAction(buttonSpec["text"].string) {
                execute(spec["onClick"], screen, this)
            }
        }
        snackbar.show()
        return true
    }
}
