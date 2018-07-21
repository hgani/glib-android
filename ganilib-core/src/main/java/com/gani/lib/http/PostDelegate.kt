package com.gani.lib.http

import java.io.IOException
import java.net.HttpURLConnection
import java.nio.charset.Charset

internal class PostDelegate(nakedUrl: String, params: GImmutableParams?, hook: HttpHook<*>, override protected val method: HttpMethod) : HttpDelegate(nakedUrl, hook) {
    private val params: GImmutableParams

    override val fullUrl: String
        get() = url

    init {
        this.params = GImmutableParams.fromNullable(params)
    }

//    protected override fun getMethod(): String {
//        return method.name
//    }

    @Throws(IOException::class)
    override fun makeConnection(): HttpURLConnection {
        val connection = GHttp.instance.listener.openConnection(fullUrl, params, method)
        connection.setDoOutput(true)

        val data = GHttp.instance.listener.processParams(params, method)
                .toMutable().put("_method", method.name).toImmutable()
                .asQueryString().toByteArray(Charset.forName("UTF-8"))
//                .getBytes("UTF-8")
        connection.getOutputStream().write(data)
        return connection
    }

    companion object {
        private val serialVersionUID = 1L
    }
}
