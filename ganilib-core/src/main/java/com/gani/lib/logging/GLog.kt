package com.gani.lib.logging

import android.util.Log

import com.gani.lib.GApp
import com.gani.lib.collection.SelfTruncatingSet

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object GLog {
    // See http://stackoverflow.com/questions/4655861/displaying-more-string-on-logcat
    //  private static int LOGCAT_CHAR_LIMIT = 4000;
    // Turn if off now because it makes debugging harder in normal circumstances.
    private val LOGCAT_CHAR_LIMIT = Integer.MAX_VALUE

    fun w(cls: Class<*>, msg: String, t: Throwable) {
        Log.w(cls.name, msg, t)
    }

    fun w(cls: Class<*>, msg: String) {
        Log.w(cls.name, msg)
    }

    fun i(cls: Class<*>, msg: String) {
        if (msg.length > LOGCAT_CHAR_LIMIT) {
            Log.i(cls.name, msg.substring(0, LOGCAT_CHAR_LIMIT))
            i(cls, msg.substring(LOGCAT_CHAR_LIMIT))
        } else {
            Log.i(cls.name, msg)
        }
    }

    fun d(cls: Class<*>, msg: String) {
        if (msg.length > LOGCAT_CHAR_LIMIT) {
            Log.d(cls.name, msg.substring(0, LOGCAT_CHAR_LIMIT))
            d(cls, msg.substring(LOGCAT_CHAR_LIMIT))
        } else {
            Log.d(cls.name, msg)
        }
    }

    fun v(cls: Class<*>, msg: String) {
        Log.v(cls.name, msg)
    }

    // Prominent logging to accomodate temporary testing.
    fun t(cls: Class<*>, msg: String) {
        i(cls, "********** $msg")
    }

    fun e(cls: Class<*>, msg: String) {
        Log.e(cls.name, msg)
    }

    fun e(cls: Class<*>, msg: String, t: Throwable) {
        Log.e(cls.name, msg, t)
    }


    // Adopted from http://stackoverflow.com/questions/27957300/read-logcat-programmatically-for-an-application
    object Reader {
        private val processIds = SelfTruncatingSet<String>(3)
        private val BLACKLISTED_STRINGS = arrayOf(" D/TextLayoutCache", " D/FlurryAgent", " D/dalvikvm")

        // See http://www.helloandroid.com/tutorials/reading-logs-programatically
        // See http://developer.android.com/tools/debugging/debugging-log.html#outputFormat
        val log: String
            get() {
                try {
                    val process = Runtime.getRuntime().exec("logcat -d -v time")
                    val log = StringBuilder("App version: " + GApp.INSTANCE.version)
                    BufferedReader(InputStreamReader(process.inputStream)).use { r ->
                        r.lineSequence().forEach {
                            println(it)
                        }
                    }
//                    var line: String
//                    while ((line = bufferedReader.readLine()) != null) {
//                        if (shouldLog(line)) {
//                            log.append(line + "\n\n")
//                        }
//                    }

                    return log.toString()
                } catch (ex: IOException) {
                    throw RuntimeException(ex)
                }

            }

        fun registerProcessId() {
            processIds.add(Integer.toString(android.os.Process.myPid()))
        }

        //    private static final String TAG = Reader.class.getCanonicalName();
        //    private static final String processId = Integer.toString(android.os.Process.myPid());

        private fun belongsToApp(line: String): Boolean {
            for (processId in processIds) {
                if (line.contains(processId)) {
                    return true
                }
            }
            return false
        }

        private fun shouldLog(line: String): Boolean {
            if (belongsToApp(line)) {
                for (blacklistedString in BLACKLISTED_STRINGS) {
                    if (line.contains(blacklistedString)) {
                        return false
                    }
                }
                return true
            }
            return false
        }
    }
}
