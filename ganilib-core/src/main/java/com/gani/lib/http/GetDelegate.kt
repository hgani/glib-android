package com.gani.lib.http

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException


internal class GetDelegate(nakedUrl: String, params: GImmutableParams?) : HttpDelegate(nakedUrl) {
    private val params: GImmutableParams
    override protected val method = HttpMethod.GET

    override val fullUrl: String
        get() {
            val url = url

            val finalParams = GHttp.instance.listener.processParams(params, method)

            if (finalParams.size() <= 0) {
                return url
            }

            val separator = if (url.contains("?")) "&" else "?"

            return url + separator + finalParams.asQueryString()
        }

    init {
        this.params = GImmutableParams.fromNullable(params)
//        this.method = HttpMethod.GET
    }

    fun getParam(key: String): Any? {
        return params[key]
    }

//    protected override fun getMethod(): String {
//        return method.name
//    }

    @Throws(MalformedURLException::class, IOException::class)
    override fun makeConnection(): HttpURLConnection {
        return GHttp.instance.listener.openConnection(fullUrl, params, method)
    }

    companion object {
        private val serialVersionUID = 1L
    }
}
