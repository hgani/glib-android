package com.glib.core.model

import android.os.Bundle
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import java.io.Serializable

class GBundle(val native: Bundle) {
    companion object {
        val KEY_SINGLETON = "__singleton"
    }

    protected constructor(backend: GBundle) : this(backend.native)

    constructor() : this(Bundle())

    fun set(value: Serializable): GBundle {
        native.putSerializable(KEY_SINGLETON, value)
        return this
    }

    val single: Serializable?
        get() = if (containsKey(KEY_SINGLETON)) { native.getSerializable(KEY_SINGLETON) } else null

//    operator fun get(key: String): Serializable? {
//        return native.getSerializable(key)
//    }

    operator fun get(key: String): Value {
        return Value(native.getSerializable(key))
    }



//    fun getIntent(key: String): Intent {
//        return native.getParcelable<Parcelable>(key)
//    }

//    fun getString(key: String): String? {
//        return native.getString(key)
//    }

//    fun getLong(key: String): Long {
//        return native.getLong(key)
//    }

//    fun getInt(key: String): Int {
//        return native.getInt(key)
//    }
//
//    fun getBoolean(key: String): Boolean {
//        if (!containsKey(key)) {
//            throw IllegalArgumentException("Bundle key not specified: $key")
//        }
//        return native.getBoolean(key)
//    }
//
//    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
//        return native.getBoolean(key, defaultValue)
//    }
//
//    fun getNullableLong(key: String): Long? {
//        return if (containsKey(key)) {
//            getLong(key)
//        } else null
//    }

    private fun containsKey(key: String): Boolean {
        return native.containsKey(key)
    }

//    fun <T> getClass(key: String): Class<T> {
//        return getSerializable(key) as Class<T>?
//    }
//
//    fun getParams(key: String): GImmutableParams {
//        return native.getSerializable(key) as GImmutableParams
//    }


    fun getSerializable(key: String): Serializable? {
        val `object` = native.getSerializable(key)
        // In lower API levels (e.g. Kitkat), T[] is returned as Object[] so we use a wrapper to retain the original array type.
        // See http://stackoverflow.com/questions/30154807/java-lang-classcastexception-java-lang-object-cannot-be-cast-to-pfe-essat-obj
        return (`object` as? ArrayWrapper<*>)?.array ?: `object`
    }

    class ArrayWrapper<T>(val array: Array<T>) : Serializable

    class Value(val serializable: Serializable?) {
        val string: String?
            get() {
                return serializable as? String
            }

        val stringValue: String
            get() {
                return string ?: ""
            }

        val bool: Boolean?
            get() {
                return serializable as? Boolean
            }

        val boolValue: Boolean
            get() {
                return bool ?: false
            }

        val json: GJson?
            get() {
                return serializable as? GJson
            }

        val jsonValue: GJson
            get() {
                return json ?: GJsonObject.Default()
            }

    }
}
