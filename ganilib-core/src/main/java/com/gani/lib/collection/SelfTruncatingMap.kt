package com.gani.lib.collection

import java.util.*

class SelfTruncatingMap<K, V>(internal val maxCapacity: Int) : LinkedHashMap<K, V>(maxCapacity) {

    override fun removeEldestEntry(eldest: Map.Entry<K, V>): Boolean {
        return size > maxCapacity
    }

    companion object {
        private val serialVersionUID = 1L
    }
}
