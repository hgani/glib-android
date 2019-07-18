package com.glib.core.json

import org.json.JSONArray
import org.json.JSONException
import java.util.*

abstract class GJsonArray<JO : GJsonObject<*, *>> : Iterable<JO> {
//    private var elements: Array<JO>? = null
    private var elementList = ArrayList<JO>()

    val size: Int
        get() = elementList.size

    val isEmpty: Boolean
        get() = size == 0

    @Throws(JSONException::class)
    protected constructor(str: String) : this(JSONArray(str)) {
    }

//    @Throws(JSONException::class)
//    constructor(elements: Array<JO>) {
//        this.elements = elements
//    }

    constructor(backend: JSONArray) {
//        val temp = ArrayList<JSONObject>()
//        for (i in 0 until backend.length()) {
//            try {
//                temp.add(backend.getJSONObject(i))
//            } catch (e: JSONException) {
//                // This shouldn't happen.
//            }
//
//        }
//
//        this.elements = createArray(temp.size)
//        for (i in temp.indices) {
//            elements[i] = createObject(temp[i])
//        }
//
//        //    for (int i = 0; i < elements.length; ++i) {
//        ////      elements[i] = new GJsonObject(backend.getJSONObject(i));
//        //      elements[i] = createObject(backend.getJSONObject(i));
//        //    }

//        this.elementList = ArrayList<JO>()

        for (i in 0 until backend.length()) {
            try {
                elementList.add(createObject(backend.getString(i)))
            } catch (e: JSONException) {
                // This shouldn't happen.
            }
        }
    }

//    protected abstract fun createArray(length: Int): Array<JO>
    protected abstract fun createObject(rawString: String?): JO

//    fun size(): Int {
//        return elements!!.size
//    }

    override fun toString(): String {
        return elementList.toString()
//        return Arrays.toString(elements)
    }

//    fun toList(): List<JO> {
//        return LinkedList(Arrays.asList(*elements!!))
//    }

    override fun iterator(): Iterator<JO> {
        return object : Iterator<JO> {
            private var index = 0

            override fun hasNext(): Boolean {
                return index < size
            }

            override fun next(): JO {
                return elementList[index++]
            }

//            override fun remove() {
//                throw UnsupportedOperationException()
//            }
        }
    }

    class Default : GJsonArray<GJsonObject.Default> {
        @Throws(JSONException::class)
        constructor(str: String) : super(str) {
        }

        constructor(array: JSONArray) : super(array) {}

//        override fun createArray(length: Int): Array<GJsonObject.Default> {
//            return arrayOfNulls(length)
//        }

        override fun createObject(rawString: String?): GJsonObject.Default {
            return GJsonObject.Default(rawString)
        }
    }
}
