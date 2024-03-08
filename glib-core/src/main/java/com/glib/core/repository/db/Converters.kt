package com.glib.core.repository.db

import androidx.room.TypeConverter
import org.json.JSONObject


class Converters {
    @TypeConverter
    fun toJSONObject(value: String): JSONObject {
        return JSONObject(value)
    }

    @TypeConverter
    fun fromJSONObject(value: JSONObject): String {
        return value.toString()
    }
}
