package com.glib.core.json

import com.glib.core.logging.GLog
import com.google.gson.internal.bind.util.ISO8601Utils
import org.json.JSONArray
import java.util.*

class GJsonBuilder {
//    companion object {
//        val KEY_SINGLETON = "__singleton"
//    }

    private val rawMap = hashMapOf<String, Any>()

    constructor(map: Map<String, Any>) {
        rawMap.putAll(map)
//        rawJson = JSONObject(map)
    }

    constructor() {
    }

//    fun set(value: Long): GJsonBuilder {
//        put(KEY_SINGLETON, value)
//        return this
//    }
//
//    fun set(value: Int): GJsonBuilder {
//        put(KEY_SINGLETON, value)
//        return this
//    }
//
//    fun set(value: String): GJsonBuilder {
//        put(KEY_SINGLETON, value)
//        return this
//    }

    fun put(key: String, value: String): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Boolean): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Int): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Long): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Date): GJsonBuilder {
        rawMap.put(key, ISO8601Utils.format(value))
        return this
    }
//
//    fun put(key: String, value: List<GJsonObject.Default>): GJsonBuilder {
//        GLog.t(javaClass, "********* LIST1: ${GJsonArray.Default(value)}")
//
//        rawMap.put(key, GJsonArray.Default(value))
//        return this
//    }

    fun put(key: String, value: List<Long>): GJsonBuilder {
        rawMap.put(key, JSONArray(value))
        return this
    }

    fun toJson(): GJsonObject.Default {
//        return GJson(rawMap)
        return GJsonObject.Default(rawMap)
    }
}

