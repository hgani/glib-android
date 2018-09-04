package com.gani.lib.http

import android.net.http.HttpResponseCache
import com.gani.lib.GBuildConfig
import com.gani.lib.io.PersistentCookieStore
import com.gani.lib.io.WebkitCookieManagerProxy
import com.gani.lib.logging.GLog
import com.gani.lib.utils.Res
import java.io.File
import java.io.IOException
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy


class GHttp {
    private fun assertInitialized(value: Any?) {
        if (value == null) {
            throw IllegalStateException("Call GHttp.INSTANCE.initialize() in App first")
        }
    }

    val listener: GHttpListener
        get() {
            assertInitialized(varListener)
            return varListener!!
        }

    val build: GBuildConfig
        get() {
            assertInitialized(varBuild)
            return varBuild!!
        }

    private var varListener: GHttpListener? = null
    private var varBuild: GBuildConfig? = null

    val host: String
        get() {
            return build.host
        }

    fun initialize(build: GBuildConfig, listener: GHttpListener) {
        this.varBuild = build
        this.varListener = listener

        initPermanentCookieHandler()
        initResponseCache()
    }

    private fun initPermanentCookieHandler() {
        android.webkit.CookieSyncManager.createInstance(Res.context)

        // Use ACCEPT_ALL instead of ACCEPT_ORIGINAL_SERVER so that it is cross-subdomain.
        val defaultManager = CookieManager(null, CookiePolicy.ACCEPT_ALL)
        val cookieStore = PersistentCookieStore(defaultManager.cookieStore)

        // See http://stackoverflow.com/questions/18057624/two-way-sync-for-cookies-between-httpurlconnection-java-net-cookiemanager-and
        CookieHandler.setDefault(WebkitCookieManagerProxy(cookieStore, CookiePolicy.ACCEPT_ALL))
    }

    // Handle 304 status
    private fun initResponseCache() {
        try {
            val httpCacheDir = File(Res.context.getCacheDir(), "http")
            val httpCacheSize = (50 * 1024 * 1024).toLong() // 50 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize)
        } catch (e: IOException) {
            GLog.e(javaClass, "Failed initializing response cache", e)
        }
    }

    companion object {
        val instance = GHttp()
    }
}
