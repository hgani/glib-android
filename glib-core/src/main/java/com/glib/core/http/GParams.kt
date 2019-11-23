package com.glib.core.http

import android.net.Uri
import com.glib.core.json.GJsonObject
import java.io.Serializable
import java.util.*

abstract class GParams<PB : GParams<PB, IP>, IP : GImmutableParams> @JvmOverloads protected constructor(initialData: Map<String, Any?> = HashMap()) : Serializable {
    // NOTE: Value should only be either String or String[]
    private val paramMap: MutableMap<String, Any?>

    protected val map: Map<String, Any?>
        get() = HashMap(paramMap)

    init {
        this.paramMap = HashMap()
        paramMap.putAll(initialData)
    }

    private fun self(): PB {
        return this as PB
    }

    fun put(name: String, value: String?): PB {
        paramMap.put(name, value)
        return self()
    }

//    private fun nullSafeString(value: Any?): String? {
//        return value?.toString()
//    }

    fun put(name: String, value: Long?): PB {
        return put(name, value?.toString())
    }

    fun put(name: String, value: Boolean?): PB {
        return put(name, value?.toString())
    }

    fun put(name: String, value: Int?): PB {
        return put(name, value?.toString())
    }

    fun put(name: String, value: GJsonObject<*, *>?): PB {
        return put(name, value?.toString())
    }

    fun put(name: String, values: Array<String>): PB {
        paramMap.put(name, values)
        return self()
    }

    operator fun get(name: String): Any? {
        return paramMap[name]
    }

    fun copy(): GParams<*, *> {
        return Default(paramMap)
    }

    fun size(): Int {
        return paramMap.size
    }

    fun entrySet(): Set<Map.Entry<String, Any?>> {
        return paramMap.entries
    }

    private fun nestedPut(prefix: String?, map: Map<String, Any?>): PB {
        for ((key, value) in map) {
            val nestedKey = if (prefix == null) key else "$prefix[$key]"
            val childMap = value as? Map<String, Any?>
            val array = value as? Array<String>
            if (childMap != null) {
                nestedPut(nestedKey, childMap)
            }
            else if (array != null) {
                put(nestedKey, array)
            }
            else {
                put(nestedKey, value?.toString())
            }
        }
        return self()
    }

    fun put(name: String, map: Map<String, Any?>): PB {
        return nestedPut(name, map)
    }

    fun merge(map: Map<String, Any?>): PB {
        return nestedPut(null, map)
    }

//    fun merge(map: Map<String, Any?>): PB {
//        for ((key, value) in map) {
////            val value = uri.getQueryParameter(key)
////            put(key, value)
//        }
//        return toImmutable()
//    }

    // Think about this class as a builder and GImmutableParams is the actual (built) object.
    protected abstract fun createImmutable(paramMap: Map<String, Any?>): IP

    fun toImmutable(): IP {
        return createImmutable(paramMap)
    }

    fun importFrom(uri: Uri): IP {
        for (key in uri.queryParameterNames) {
            val value = uri.getQueryParameter(key)
            put(key, value)
        }
        return toImmutable()
    }

    override fun toString(): String {
        return toImmutable().toString()
    }

    class Default : GParams<Default, GImmutableParams> {
        constructor() : super() {}

        constructor(initialData: Map<String, Any?>) : super(initialData) {}

        override fun createImmutable(paramMap: Map<String, Any?>): GImmutableParams {
            return GImmutableParams(paramMap)
        }
    }

    companion object {

        fun create(): GParams<*, *> {
            return Default()
        }

        fun fromNullable(params: GImmutableParams?): GParams<*, *> {
            return if (params == null) {
                GParams.create()
            } else params.toMutable()
        }
    }
}
