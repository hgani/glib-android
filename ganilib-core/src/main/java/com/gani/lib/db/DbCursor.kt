package com.gani.lib.db

import android.database.Cursor
import com.gani.lib.GApp
import com.gani.lib.logging.GLog
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class GDbCursor(private val cursor: Cursor?) {
    companion object {
        val TRUE = 1
        val FALSE = 0
    }

    protected fun <T, C : GDbCursor> executeFirstRow(command: AutoCleanupCommand<T, C>): T {
        try {
            moveToFirst()
            return command.execute(this as C)
        } finally {
            close()
        }
    }

    protected fun <T, C : GDbCursor> executeFirstRowIfExist(command: AutoCleanupCommand<T, C>): T? {
        try {
            return if (moveToFirst()) {
                command.execute(this as C)
            } else null
        } finally {
            close()
        }
    }

    interface AutoCleanupCommand<T, C : GDbCursor> {
        fun execute(cursor: C): T
    }

    // For debugging
    fun logColumnNames() {
        val columnNames = cursor!!.columnNames
        for (name in columnNames) {
            GLog.t(javaClass, "Column " + name + " => " + cursor.getColumnIndex(name))
        }
    }

    fun moveToFirst(): Boolean {
        return cursor != null && cursor.moveToFirst()
    }

    fun moveToPosition(i: Int): Boolean {
        return cursor != null && cursor.moveToPosition(i)
    }

    fun moveToPrevious(): Boolean {
        return cursor != null && cursor.moveToPrevious()
    }

    fun moveToNext(): Boolean {
        return cursor != null && cursor.moveToNext()
    }

    fun getString(index: Int): String {
        return cursor!!.getString(index)
    }

    fun getBoolean(index: Int): Boolean {
        return cursor!!.getInt(index) == TRUE
    }

    fun getLong(index: Int): Long {
        return cursor!!.getLong(index)
    }

    fun getInt(index: Int): Int {
        return cursor!!.getInt(index)
    }

    fun getNullableInt(index: Int): Int? {
        return if (cursor!!.isNull(index)) null else cursor.getInt(index)
    }

    fun isNull(name: String): Boolean {
        return cursor!!.isNull(getIndex(name))
    }

    fun close() {
        cursor?.close()
    }

    fun getIndex(name: String): Int {
        return cursor!!.getColumnIndex(name)
    }

    fun getString(name: String): String {
        return cursor!!.getString(getIndex(name))
    }

    fun getNullableString(name: String): String? {
        return if (isNull(name)) null else getString(name)
    }

    fun getBoolean(name: String): Boolean {
        return getBoolean(getIndex(name))
    }

    fun getNullableBoolean(index: Int): Boolean? {
        return if (cursor!!.isNull(index)) null else getBoolean(index)
    }

    fun getNullableBoolean(name: String): Boolean? {
        return getNullableBoolean(getIndex(name))
    }

    fun getInt(name: String): Int {
        return getInt(getIndex(name))
    }

    fun getNullableInt(name: String): Int? {
        return getNullableInt(getIndex(name))
    }

    fun getLong(name: String): Long {
        return getLong(getIndex(name))
    }

    fun getNullableLong(index: Int): Long? {
        return if (cursor!!.isNull(index)) null else cursor.getLong(index)
    }

    fun getNullableLong(name: String): Long? {
        return getNullableLong(getIndex(name))
    }

    //  public Uri getInternalUrl(String pathKey) {
    //    return Uri.parse(Build.INSTANCE.getWebPrefix() + getString(pathKey));
    //  }

    // See https://code.google.com/p/guava-libraries/wiki/ReflectionExplained
    fun <T> getObject(name: String, typeToken: TypeToken<T>): T? {
        return getObject(getIndex(name), typeToken)
    }

    fun <T> getObject(index: Int, typeToken: TypeToken<T>): T? {
        // Beware that incompatible objects might not throw an exception, e.g. a field that exists on POJO but not on JSON.
        // In this case, the field will be null, which can cause problems.
        // See http://stackoverflow.com/questions/3163193/strict-json-parsing-with-googles-gson
        try {
            return GApp.gson().fromJson(getString(index), typeToken.type)
        } catch (e: JsonParseException) {
            GLog.w(javaClass, "Failed parsing stored object: ", e)
            return null
        } catch (e: IllegalArgumentException) {
            GLog.w(javaClass, "Incompatible stored object: ", e)
            return null
        }

    }
}
