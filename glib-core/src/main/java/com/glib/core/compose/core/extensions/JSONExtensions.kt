package com.glib.core.compose.core.extensions

import androidx.compose.ui.graphics.Color
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun JSONObject.nullableString(key: String): String? {
    return try {
        this.getString(key)
    } catch (e: JSONException) {
        return null
    }
}

fun JSONObject.string(key: String, defaultValue: String = ""): String {
    return nullableString(key = key) ?: defaultValue
}

fun JSONObject.nullableInt(key: String): Int? {
    return try {
        this.getInt(key)
    } catch (e: JSONException) {
        return null
    }
}

fun JSONObject.nullableBoolean(key: String): Boolean? {
    return try {
        this.getBoolean(key)
    } catch (e: JSONException) {
        return null
    }
}

fun JSONObject.nullableColor(key: String): Color? {
    return try {
        val colorHex = this.nullableString(key = key) ?: return null
        Color(android.graphics.Color.parseColor(colorHex))
    } catch (e: IllegalArgumentException) {
        return null
    }
}

fun JSONObject.sliceJsonObject(key: String): JSONObject? {
    return try {
        this.getJSONObject(key)
    } catch (e: JSONException) {
        null
    }
}


private fun JSONObject.sliceJsonArray(key: String): JSONArray? {
    return try {
        this.getJSONArray(key)
    } catch (e: JSONException) {
        null
    }
}


fun JSONArray.toJSONObjectList(): List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until this.length()) {
        list.add(this.getJSONObject(i))
    }
    return list.toList()
}


/**
 * @param key (Make sure the key is for `JSONArray` and not the `JSONObject` otherwise return value will be null)
 * @return List<JSONObject>?
 * */
fun JSONObject.nullableObjectList(key: String): List<JSONObject>? =
    this.sliceJsonArray(key = key)?.toJSONObjectList()

fun JSONObject.objectList(key: String): List<JSONObject> {
    return nullableObjectList(key = key) ?: listOf()
}

//fun JSONObject.json(key: String): JSONObject? {
//    return getJSONObject(key)
//}

//private fun JSONObject.string(name: String): String? {
//    try {
//        // isNull() is needed to check if the property is explicitly specified as null.
//        // If the property is not specified (i.e. undefined), we'll get JSONException.
//        return if (isNull(name)) null else strictString(name)
//    } catch (e: JSONException) {
//        return null
//    }
//}
//
//@Throws(JSONException::class)
//private fun JSONObject.strictString(name: String): String {
//    return getString(name)
//}
fun JSONObject.nullableObject(key: String): JSONObject? {
    return getJSONObject(key)
}

//// Only use to getComponents from JSONObject
//fun JSONObject.sliceComponents(): JSONArray? {
//    return this.sliceJsonArray("components")
//}