package com.glib.core.collection

import com.glib.core.json.GGson
import com.google.gson.*
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.lang.reflect.Type
import java.util.*

class SelfTruncatingSet<E>(maxCapacity: Int) : AbstractSet<E>(), Serializable {

    private val backingMap: SelfTruncatingMap<E, Any>

    private val maxCapacity: Int
        get() = backingMap.maxCapacity

    init {
        this.backingMap = SelfTruncatingMap(maxCapacity)
    }


    override fun add(`object`: E): Boolean {
        return backingMap.put(`object`, VALUE) === VALUE
    }

    override fun clear() {
        backingMap.clear()
    }

//    override fun clone(): Any {
//        // If one day we need to support this, see HashSet implementation.
//        throw UnsupportedOperationException()
//    }

//    override operator fun contains(`object`: Any): Boolean {
//        return backingMap.containsKey(`object`)
//    }

    override fun contains(element: E): Boolean {
        return backingMap.containsKey(element)
    }

    override fun isEmpty(): Boolean {
        return backingMap.isEmpty()
    }

    override fun iterator(): MutableIterator<E> {
        return backingMap.keys.iterator()
    }

    override fun remove(element: E): Boolean {
        return super.remove(element)
    }

//    override fun remove(`object`: Any): Boolean {
//        return backingMap.remove(`object`) != null
//    }

    override val size: Int
        get() = backingMap.size

//    override fun size(): Int {
//        return backingMap.size
//    }

    @Throws(IOException::class)
    private fun writeObject(stream: ObjectOutputStream) {
        // If one day we need to support this, see HashSet implementation.
        throw UnsupportedOperationException()
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(stream: ObjectInputStream) {
        // If one day we need to support this, see HashSet implementation.
        throw UnsupportedOperationException()
    }

    internal fun createBackingMap(capacity: Int, loadFactor: Float): HashMap<E, HashSet<E>> {
        return HashMap(capacity, loadFactor)
    }


    class GsonSerializer : JsonSerializer<SelfTruncatingSet<*>>, JsonDeserializer<SelfTruncatingSet<*>> {
        override fun serialize(src: SelfTruncatingSet<*>, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            val `object` = JsonObject()
            `object`.addProperty("max_capacity", src.maxCapacity)
            `object`.addProperty("delegate", GGson.defaultGson().toJson(src))
            return `object`
        }

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): SelfTruncatingSet<*> {
            val jsonObject = json.asJsonObject
            val maxCapacity = jsonObject.get("max_capacity").asInt
            val delegate = jsonObject.get("delegate").asString

            val set = SelfTruncatingSet<Any>(maxCapacity)
            // Use typeOfT so that the generic type is still maintained, e.g. LinkedHashSet<Long>
            val entries = GGson.defaultGson().fromJson<LinkedHashSet<*>>(delegate, typeOfT)
            for (entry in entries) {
                set.add(entry)
            }
            return set
        }
    }

    companion object {
        private const val serialVersionUID = 1L
        private val VALUE = Any()
    }
}
