package com.glib.core.json

import com.glib.core.model.GBundle
import com.google.gson.internal.bind.util.ISO8601Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.math.BigDecimal
import java.text.ParseException
import java.text.ParsePosition
import java.util.*

typealias GJson = GJsonObject<*, *>
typealias GJsonDefault = GJsonObject.Default

//abstract class GJsonObject<JO : GJsonObject<JO, JA>, JA : GJsonArray<JO>> @JvmOverloads protected constructor(val rawString: String? = null) {
abstract class GJsonObject<JO : GJsonObject<JO, JA>, JA : GJsonArray<JO>> : Serializable {
    private val rawString: String?

    companion object {
        private fun rawJsonFrom(rawString: String?): JSONObject {
            try {
                return JSONObject(rawString)
            } catch (e: JSONException) {
                // Use default
            } catch (e: NullPointerException) {
                // Use default
            }
            return JSONObject()
        }
    }

    @Transient
    private var rawJson: JSONObject
        private set

    protected constructor(map: Map<String, Any>) {
        val rawJson = JSONObject(map)
        this.rawString = rawJson.toString()
        this.rawJson = rawJson
    }

    protected constructor(rawString: String? = null) {
        this.rawString = rawString
        this.rawJson = rawJsonFrom(rawString)
    }

//    fun set(value: Serializable): GBundle {
//        native.putSerializable(GBundle.KEY_SINGLETON, value)
//        return this
//    }

//    val single: Serializable
//        get() {
//            return get(KEY_SINGLETON)
//        }

    val array: JA?
        get() {
            if (rawString == null) {
                return null
            }
            try {
                return createArray(JSONArray(rawString))
            } catch (e: JSONException) {
                return null
            }
        }

    val arrayValue: JA
        get() {
            return array ?: createArray(JSONArray())
        }

    val string: String?
        get() {
            return rawString
        }

    val stringValue: String
        get() {
            return rawString ?: ""
        }

    val date: Date?
        get() {
            try {
                return ISO8601Utils.parse(stringValue, ParsePosition(0))
            } catch (e: ParseException) {
                return null
            }
        }

    val dateValue: Date
        get() {
            return date ?: Date()
        }

    val int: Int?
        get() {
            try {
                return stringValue.toInt()
            } catch (e: NumberFormatException) {
                return null
            }
        }

    val intValue: Int
        get() {
            return int ?: 0
        }

    val long: Long?
        get() {
            try {
                return stringValue.toLong()
            } catch (e: NumberFormatException) {
                return null
            }
        }

    val longValue: Long
        get() {
            return long ?: 0
        }

    val double: Double?
        get() {
            try {
                return stringValue.toDouble()
            } catch (e: NumberFormatException) {
                return null
            }
        }

    val doubleValue: Double
        get() {
            return double ?: 0.0
        }

    val decimal: BigDecimal?
        get() {
            try {
                return BigDecimal(string)
            } catch (e: NumberFormatException) {
                return null
            }
        }

    val decimalValue: BigDecimal
        get() {
            return decimal ?: BigDecimal.ZERO
        }

    val bool: Boolean?
        get() {
            if (stringValue.equals("true", ignoreCase = true)) {
                return true
            } else if (stringValue.equals("false", ignoreCase = true)) {
                return false
            } else {
                return null
            }
        }

    val boolValue: Boolean
        get() {
            return bool ?: false
        }

    val presence: JO?
        get() {
            return if (isNull()) null else (this as? JO)
        }

    fun isNull() : Boolean {
        return rawString == null
    }

    protected abstract fun createArray(array: JSONArray): JA
    protected abstract fun createObject(rawString: String?): JO

    fun clone(): JO {
        return createObject(rawString)
    }

    fun merge(map: Map<String, Any>): JO {
        val clone = clone()
        val cloneJson = clone.rawJson

        for ((k, v) in map) {
            // Merge nested keys, e.g. "formData[authenticity_token]"
            val keys = k.trimEnd(']').split("[", "][")
            var json = cloneJson
            for (key in keys.dropLast(1)) {
                try {
                    json = json.getJSONObject(key)
                } catch (e: JSONException) {
                    val parentJson = JSONObject()
                    json.put(key, parentJson)
                    json = parentJson
                }
            }
            json.put(keys.last(), v)
        }
        return clone
    }

    fun keys(): Iterator<String> {
        return rawJson.keys()
    }

    /////

    operator fun get(name: String): JO {
        return createObject(string(name))
    }

    private fun string(name: String): String? {
        try {
            // isNull() is needed to check if the property is explicitly specified as null.
            // If the property is not specified (i.e. undefined), we'll get JSONException.
            return if (rawJson.isNull(name)) null else strictString(name)
        } catch (e: JSONException) {
            return null
        }
    }

    @Throws(JSONException::class)
    private fun strictString(name: String): String {
        return rawJson.getString(name)
    }

//    fun color(name: String): Int? {
//        val code = string(name)
//        if (code != null) {
//            try {
//                return Ui.color(code)
//            } catch (e: IllegalArgumentException) {
//                // Do nothing
//            }
//        }
//        return null
//    }
//

    /////


    //  public JO put(String name, String value) {
    //    try {
    //      backend.put(name, value);
    //    }
    //    catch (JSONException e) {
    //      GLog.e(getClass(), "Failed adding value to JSON", e);
    //    }
    //    return self();
    //  }

//    fun put(name: String, value: Any): JO {
//        var value = value
//        try {
//            if (value is GJsonObject<*, *>) {
//                value = value.backend
//            }
//            backend.put(name, value)
//        } catch (e: JSONException) {
//            GLog.e(javaClass, "Failed adding value to JSON", e)
//        }
//
//        return self()
//    }

    override fun toString(): String {
        return rawString ?: "<null>"
    }

    @Throws(IOException::class)
    private fun writeObject(oos: ObjectOutputStream) {
        oos.defaultWriteObject()
    }

    @Throws(ClassNotFoundException::class, IOException::class)
    private fun readObject(ois: ObjectInputStream) {
        ois.defaultReadObject()
        this.rawJson = rawJsonFrom(rawString)
    }



    class Default : GJsonObject<GJsonObject.Default, GJsonArray.Default> {
        constructor(rawString: String? = null) : super(rawString)

        constructor(map: Map<String, Any>) : super(map)

        override fun createArray(array: JSONArray): GJsonArray.Default {
            return GJsonArray.Default(array)
        }

        override fun createObject(rawString: String?): GJsonObject.Default {
            return GJsonObject.Default(rawString)
        }
    }
}

