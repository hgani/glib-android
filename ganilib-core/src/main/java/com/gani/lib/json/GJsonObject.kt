package com.gani.lib.json

import com.google.gson.internal.bind.util.ISO8601Utils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.ParsePosition
import java.util.*

abstract class GJsonObject<JO : GJsonObject<JO, JA>, JA : GJsonArray<JO>> @JvmOverloads protected constructor(val rawString: String? = null) {
    private var rawJson: JSONObject? = null

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

//    val isEmpty: Boolean
//        get() = if (backend.names() == null) {
//            true
//        } else backend.names().length() <= 0
//
//    protected constructor(`object`: GJsonObject<*, *>) : this(`object`.backend) {}
//
//    @Throws(JSONException::class)
//    protected constructor(rawString: String) : this(JSONObject(rawString)) {
//    }

//    // Return GJsonArray instead of List<GJsonObject> so we can provide additional info such as
//    // overriding toString().
//    @Throws(JSONException::class)
//    fun getArray(name: String): JA {
//        return createArray(getRawArray(name))
//    }
//
//    protected abstract fun createArray(array: JSONArray): JA
//
//    @Throws(JSONException::class)
//    private fun getRawArray(name: String): JSONArray {
//        return backend.getJSONArray(name)
//    }

//    @Throws(JSONException::class)
//    fun getStringArray(name: String): Array<String> {
//        val arr = backend.getJSONArray(name)
//        val elements = arrayOfNulls<String>(arr.length())
//        for (i in elements.indices) {
//            elements[i] = arr.getString(i)
//        }
//        return elements
//    }
//
//    @Throws(JSONException::class)
//    fun getNullableStringArray(name: String): Array<String>? {
//        try {
//            //      JSONArray arr = backend.getJSONArray(name);
//            //      String[] elements = new String[arr.length()];
//            //      for (int i = 0; i < elements.length; ++i) {
//            //        elements[i] = arr.getString(i);
//            //      }
//            //      return elements;
//            return getStringArray(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }

//    @Throws(JSONException::class)
//    fun getIntArray(name: String): IntArray {
//        val arr = backend.getJSONArray(name)
//        val elements = IntArray(arr.length())
//        for (i in elements.indices) {
//            elements[i] = arr.getInt(i)
//        }
//        return elements
//    }
//
//    fun getNullableIntArray(name: String): IntArray? {
//        try {
//            return getIntArray(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }

    protected abstract fun createArray(array: JSONArray): JA
    protected abstract fun createObject(rawString: String?): JO

    /////

    operator fun get(name: String): JO {
        if (rawJson == null) {
            try {
                rawJson = JSONObject(rawString)
            } catch (e: JSONException) {
                rawJson = JSONObject()
            }
        }
        return createObject(string(name))
    }

    // TODO: Deprecate
    fun json(name: String): JO {
        if (rawJson == null) {
            try {
                rawJson = JSONObject(rawString)
            } catch (e: JSONException) {
                rawJson = JSONObject()
            }
        }
        return createObject(string(name))
    }

    private fun string(name: String): String? {
        try {
            // isNull() is needed to check if the property is explicitly specified as null.
            // If the property is not specified (i.e. undefined), we'll get JSONException.
            return if (rawJson!!.isNull(name)) null else strictString(name)
        } catch (e: JSONException) {
            return null
        }
    }

    @Throws(JSONException::class)
    private fun strictString(name: String): String {
        return rawJson!!.getString(name)
    }

//
//    @Throws(JSONException::class)
//    private fun getRawObject(name: String): JSONObject {
//        return backend.getJSONObject(name)
//    }
//
//    protected fun isNull(name: String): Boolean {
//        return backend.isNull(name)
//    }
//
//    @Throws(JSONException::class)
//    fun strictObj(name: String): JO {
//        return createObject(getRawObject(name))
//    }
//
//    fun obj(name: String): JO? {
//        try {
//            return if (isNull(name)) null else strictObj(name)
//        } catch (e: JSONException) {
//            return null
//        }
//    }
//
//    fun map(name: String): JO {
//        return obj(name) ?: createObject(JSONObject())
//    }
//
//    fun keys(): Iterable<String> {
//        return object : Iterable<String> {
//            override fun iterator(): Iterator<String> {
//                return backend.keys()
//            }
//        }
//    }
//
//    @Throws(JSONException::class)
//    fun getLongs(name: String): List<Long> {
//        val array = backend.getJSONArray(name)
//        val elements = ArrayList<Long>(array.length())
//        for (i in 0 until array.length()) {
//            elements.add(array.getLong(i))
//        }
//        return elements
//    }
//
//    @Throws(JSONException::class)
//    private fun strictString(name: String): String {
//        return backend.getString(name)
//    }
//
//    fun string(name: String): String? {
//        try {
//            // isNull() is needed to check if the property is explicitly specified as null.
//            // If the property is not specified (i.e. undefined), we'll get JSONException.
//            return if (backend.isNull(name)) null else strictString(name)
//        } catch (e: JSONException) {
//            return null
//        }
//    }
//
//    fun stringValue(name: String): String {
//        return string(name) ?: ""
//    }
//
//    @Throws(JSONException::class)
//    fun getLong(name: String): Long {
//        return backend.getLong(name)
//    }
//
//    @Throws(JSONException::class)
//    fun getInt(name: String): Int {
//        return backend.getInt(name)
//    }
//
//    @Throws(JSONException::class)
//    fun getBoolean(name: String): Boolean {
//        return backend.getBoolean(name)
//    }
//
//    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
//        try {
//            return if (backend.isNull(name)) {
//                defaultValue
//            } else {
//                getBoolean(name)
//            }
//        } catch (e: JSONException) {
//            return defaultValue
//        }
//
//    }
//
//    @Throws(JSONException::class)
//    fun getDouble(name: String): Double {
//        return backend.getDouble(name)
//    }
//
//    fun getNullableInt(name: String): Int? {
//        try {
//            return if (backend.isNull(name)) null else getInt(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }
//
//    fun getInt(name: String, defaultValue: Int): Int {
//        try {
//            return if (backend.isNull(name)) defaultValue else getInt(name)
//        } catch (e: JSONException) {
//            return defaultValue
//        }
//
//    }
//
//    fun getNullableLong(name: String): Long? {
//        try {
//            return if (backend.isNull(name)) null else getLong(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }
//
//    fun getNullableDouble(name: String): Double? {
//        try {
//            return if (backend.isNull(name)) null else getDouble(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }
//
//    fun getNullableBoolean(name: String): Boolean? {
//        try {
//            return if (backend.isNull(name)) null else getBoolean(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }
//
//    fun getDouble(name: String, defaultValue: Double): Double {
//        try {
//            return if (backend.isNull(name)) defaultValue else getDouble(name)
//        } catch (e: JSONException) {
//            return defaultValue
//        }
//
//    }
//
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
//    fun getNullableArray(name: String): JA? {
//        try {
//            return if (backend.isNull(name)) null else getArray(name)
//        } catch (e: JSONException) {
//            return null
//        }
//
//    }
//
//    fun getArray(name: String, defaultValue: JA): JA {
//        try {
//            return if (backend.isNull(name)) defaultValue else getArray(name)
//        } catch (e: JSONException) {
//            return defaultValue
//        }
//
//    }
//
//    @Throws(JSONException::class)
//    fun strictDate(name: String): Date {
//        try {
//            return ISO8601Utils.parse(strictString(name), ParsePosition(0))
//        } catch (e: ParseException) {
//            throw JSONException(e.localizedMessage)
//        }
//    }
//
//    fun date(name: String): Date? {
//        try {
//            return if (backend.isNull(name)) null else strictDate(name)
//        } catch (e: JSONException) {
//            return null
//        }
//    }

    /////

//    private fun self(): JO {
//        return this as JO
//    }

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
//        return backend.toString()
    }


    class Default : GJsonObject<GJsonObject.Default, GJsonArray.Default> {
//        @Throws(JSONException::class)
        constructor(rawString: String?) : super(rawString) {
        }

        override fun createArray(array: JSONArray): GJsonArray.Default {
            return GJsonArray.Default(array)
        }

        override fun createObject(rawString: String?): GJsonObject.Default {
            return GJsonObject.Default(rawString)
        }
    }
}

