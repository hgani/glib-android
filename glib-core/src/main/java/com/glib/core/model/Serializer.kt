package com.glib.core.model

import java.io.*

object Serializer {
    fun toBytes(obj: Serializable): ByteArray {
        val bos =  ByteArrayOutputStream()
        var output: ObjectOutput? = null
        try {
            output = ObjectOutputStream(bos)
            output.writeObject(obj)
            output.flush();
            return bos.toByteArray()
        } finally {
            output?.close()
        }
    }


    fun toObj(bytes: ByteArray): Any {
        val bis = ByteArrayInputStream(bytes)
        var input: ObjectInput? = null
        try {
            input = ObjectInputStream(bis)
            return input.readObject()
        } finally {
            input?.close()
        }
    }
}
