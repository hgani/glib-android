package com.glib.core.utils

import com.glib.core.db.DbMap

object GAuth {
    var csrfToken: String?
        get() = DbMap.get(GKeys.Db.CSRF_TOKEN).string
        set(value) {
            if (value == null) {
                DbMap.remove(GKeys.Db.CSRF_TOKEN)
            }
            else {
                DbMap.put(GKeys.Db.CSRF_TOKEN, value)
            }
        }

    fun signedIn(): Boolean {
        return csrfToken != null
    }
}
