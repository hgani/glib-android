package com.gani.lib

import android.content.Context
import android.content.pm.PackageManager
import android.os.Handler
import com.gani.lib.collection.SelfTruncatingSet
import com.gani.lib.utils.Res
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class GApp {
    companion object {
        val INSTANCE = GApp()

        private val GSON = GsonBuilder().registerTypeAdapter(SelfTruncatingSet::class.java, SelfTruncatingSet.GsonSerializer()).create()
        private val DEFAULT_GSON = Gson()

        fun gson(): Gson {
            return GSON
        }

        fun defaultGson(): Gson {
            return DEFAULT_GSON
        }
    }

    lateinit var version: String
        private set

    // NOTE: Make sure the handler is created in UI thread.
    fun initialize(context: Context, handler: Handler) {
        Res.init(context);
//        Ui.init(handler);

        this.version = extractAppVersionName(context)
    }

    fun notificationIconRes(): Int {
        return R.drawable.icon_notification_logo
    }

    private fun extractAppVersionName(context: Context): String {
        lateinit var versionName: String
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            versionName = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            versionName = "unknown"
        }

        return versionName
    }
}
