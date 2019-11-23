package com.glib.core.utils.string

import android.text.Editable
import com.glib.core.io.ResourceCloser
import java.io.*

object StringUtils {
    ///// Copied from org.apache.commons.lang.StringUtils /////

    val EMPTY = ""
    fun capitalize(input: String): String {
        return input.substring(0, 1).toUpperCase() + input.substring(1)
    }

    fun toString(inputStream: InputStream): String {
        val inputBuffer = BufferedReader(InputStreamReader(inputStream))
        val outputBuffer = StringBuilder()

        try {
//            var line: String? = null
//            while ((line = inputBuffer.readLine()) != null) {
//                outputBuffer.append(line).append("\n")
//            }

            inputBuffer.lineSequence().forEach {
                outputBuffer.append(it).append("\n")
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } finally {
            ResourceCloser.close(inputBuffer)
        }

        return outputBuffer.toString()
    }

    fun writeString(value: String, outputStream: OutputStream) {
        try {
            outputStream.write(value.toByteArray(charset("UTF-8")))
        } catch (e: IOException) {
            throw RuntimeException(e)
        } finally {
            ResourceCloser.close(outputStream)
        }
    }

    fun ellipsize(text: String, maxLength: Int): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength - 3) + "..."
        } else {
            text
        }
    }

    fun textNoMoreThan(text: String, maxLength: Int): String {
        return if (text.length > maxLength) text.substring(0, maxLength) else text
    }

    // Copied from Spring Framework
    fun hasLength(str: CharSequence?): Boolean {
        return str != null && str.length > 0
    }

    // Copied from Spring Framework
    fun hasText(str: CharSequence): Boolean {
        if (!hasLength(str)) {
            return false
        }
        val strLen = str.length
        for (i in 0 until strLen) {
            if (!Character.isWhitespace(str[i])) {
                return true
            }
        }
        return false
    }

    fun presence(editable: Editable?): String? {
        val str = editable.toString()
        if (hasText(str)) {
            return str
        }
        return null
    }

    fun join(collection: Collection<Any>, separator: String): String? {
        return join(collection.toTypedArray(), separator)
    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use
     * @return the joined String, `null` if null array input
     * @since 2.0
     */
    fun join(array: Array<Any>?, separator: String): String? {
        return if (array == null) {
            null
        } else join(array, separator, 0, array.size)

    }

    /**
     *
     * Joins the elements of the provided array into a single String
     * containing the provided list of elements.
     *
     *
     * No delimiter is added before or after the list.
     * Null objects or empty strings within the array are represented by
     * empty strings.
     *
     * <pre>
     * StringUtils.join(null, *)               = null
     * StringUtils.join([], *)                 = ""
     * StringUtils.join([null], *)             = ""
     * StringUtils.join(["a", "b", "c"], ';')  = "a;b;c"
     * StringUtils.join(["a", "b", "c"], null) = "abc"
     * StringUtils.join([null, "", "a"], ';')  = ";;a"
    </pre> *
     *
     * @param array  the array of values to join together, may be null
     * @param separator  the separator character to use
     * @param startIndex the first index to start joining from.  It is
     * an error to pass in an end index past the end of the array
     * @param endIndex the index to stop joining from (exclusive). It is
     * an error to pass in an end index past the end of the array
     * @return the joined String, `null` if null array input
     * @since 2.0
     */
    fun join(array: Array<Any>?, separator: String, startIndex: Int, endIndex: Int): String? {
        if (array == null) {
            return null
        }
        var bufSize = endIndex - startIndex
        if (bufSize <= 0) {
            return EMPTY
        }

        bufSize *= (if (array[startIndex] == null) 16 else array[startIndex].toString().length) + 1
        val buf = StringBuffer(bufSize)

        for (i in startIndex until endIndex) {
            if (i > startIndex) {
                buf.append(separator)
            }
            if (array[i] != null) {
                buf.append(array[i])
            }
        }
        return buf.toString()
    }
}
