package com.glib.core.json

import com.google.gson.internal.bind.util.ISO8601Utils
import java.util.*

class GJsonBuilder {
//    private val rawJson: JSONObject
    private val rawMap = hashMapOf<String, Any>()

    constructor(map: Map<String, Any>) {
        rawMap.putAll(map)
//        rawJson = JSONObject(map)
    }

    constructor() {
    }

    fun put(key: String, value: String): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Int): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: Boolean): GJsonBuilder {
        rawMap.put(key, value)
        return this
    }

    fun put(key: String, value: List<Long>): GJsonBuilder {
        rawMap.put(key, value.toLongArray())
        return this
    }
    fun put(key: String, value: Date): GJsonBuilder {
        rawMap.put(key, ISO8601Utils.format(value))
        return this
    }

    fun toJson(): GJson {
//        return GJson(rawMap)
        return GJsonObject.Default(rawMap)
    }
}

