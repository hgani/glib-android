package com.gani.lib.http

import java.io.Serializable
import java.util.*

class GImmutableParams @JvmOverloads constructor(initialData: Map<String, Any?> = HashMap()) : Serializable {

    // NOTE: Value should only be either String or String[]
    private val paramMap = HashMap<String, Any>()

//    init {
//        this.paramMap = HashMap()
//        paramMap.putAll(initialData)
//    }
    init {
        for ((key, value) in initialData) {
            if (value != null) {
                paramMap.put(key, value)
            }
        }
    }

    protected val map: Map<String, Any>
        get() = HashMap(paramMap)


    operator fun get(name: String): Any? {
        return paramMap[name]
    }

    fun size(): Int {
        return paramMap.size
    }

    fun entrySet(): Set<Map.Entry<String, Any>> {
        return paramMap.entries
    }

    fun toMutable(): GParams<*, *> {
        return GParams.Default(paramMap)
    }

    fun asQueryString(): String {
        val buffer = StringBuilder()

        for ((key, value) in paramMap) {
//            if (value == null) {
//                GLog.e(UrlUtils::class.java, "Null param value for key " + key)
//            } else {
                if (value is String) {
                    buffer.append("&").append(key).append("=").append(UrlUtils.encodeUrl(value))
                } else {
                    //          // Note that when the array is empty, this is skipped entirely or else it will pass an array with blank string to the server
                    //          for (String value : (String[]) entry.getValue()) {
                    //            buffer.append("&").append(entry.getKey()).append("=").append(encodeUrl(value));
                    //          }
                    val values = value as Array<String>
                    if (values.size > 0) {
                        for (value in values) {
                            buffer.append("&").append(key).append("=").append(UrlUtils.encodeUrl(value))
                        }
                    } else {
                        // This solves 2 issues:
                        // - Allow server implementation to be more predictable as the param key (e.g. params[handshake][key]) always exists.
                        // - Even more important is that if this is the only param key (e.g. params[handshake][key]), not passing this will make the whole param (params[handshake]) to be missing.
                        //
                        // The only drawback is that in Rails, this will be received as `[""]` (an array with one empty string), but this can be consistently solved as it also applies to web form.
                        buffer.append("&").append(key).append("=")
                    }
                }
//            }
        }

        return if (buffer.length == 0) "" else buffer.substring(1)
    }

    override fun toString(): String {
        return asQueryString()
    }

    companion object {
        val EMPTY = GImmutableParams()

        fun fromNullable(params: GImmutableParams?): GImmutableParams {
            return params ?: GImmutableParams.EMPTY
        }
    }

    //
    //  public static GImmutableParams fromUri(Uri uri) {
    //    GParams params = GParams.create();
    //    for (String key : uri.getQueryParameterNames()) {
    //      String value = uri.getQueryParameter(key);
    //      params.put(key, value);
    //    }
    //    return params.toImmutable();
    //  }
}
