package com.glib.core.utils

import com.glib.core.db.DbMap
import java.util.*

object Device {
    private val DBKEY_DEVICE_ID = "__device_id"

    //    // Avoid using permanent device ID. See https://developer.android.com/training/articles/user-data-ids
    val id: String = extractDeviceId()
    val localeCountry: String = Res.resources.configuration.locale.country

    val os = "android"

    // See https://developer.android.com/training/articles/user-data-ids
    private fun extractDeviceId(): String {
        var deviceId = DbMap.get(DBKEY_DEVICE_ID).string
        if (deviceId == null) {
            deviceId = UUID.randomUUID().toString()
            DbMap.put(DBKEY_DEVICE_ID, deviceId)
        }
        return deviceId
    }
}
