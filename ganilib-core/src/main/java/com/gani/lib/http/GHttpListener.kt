package com.gani.lib.http

import android.webkit.WebView
import org.json.JSONException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

abstract class GHttpListener {
    fun jsonErrorMessage(url: String, ex: JSONException): String {
        return "JSON error"
    }

    fun networkErrorMessage(): String {
        return "Network error"
    }

    @Throws(IOException::class)
    fun openConnection(url: String, params: GImmutableParams, method: HttpMethod): HttpURLConnection {
        val connection = URL(url).openConnection() as HttpURLConnection
        if (url.startsWith(GHttp.instance.host)) {
            prepareConnection(connection, params, method)
        }
        return connection
    }

    open fun prepareConnection(connection: HttpURLConnection, params: GImmutableParams, method: HttpMethod) {
        // To be overridden
    }

    open fun processParams(params: GImmutableParams, method: HttpMethod): GImmutableParams {
        // To be overridden
        return params
    }

    // To be overridden
    fun createHttpResponse(url: String): GHttpResponse.Default {
        return GHttpResponse.Default(url)
    }

//    // To be overridden
//    fun alertHelper(): GHttpAlert<*, *> {
//        return object : GHttpAlert() {
//            @Throws(JSONException::class)
//            override fun reportCodeError(r: GHttpResponse<*>) {
//
//            }
//
//            override fun reportJsonError(r: GRestResponse, e: JSONException) {
//
//            }
//
//            override fun messageForJsonError(url: String, ex: JSONException): String {
//                return null
//            }
//
//            //      @Override
//            //      public void alertJsonError(Context c, GRestResponse r, JSONException e) {
//            //
//            //      }
//
//            @Throws(JSONException::class)
//            override fun alertCommonError(context: Context, r: GHttpResponse<*>) {
//
//            }
//        }
//    }

    //  public abstract String baseUrl();

    fun prepareWebView(webView: WebView) {
        // To be overridden.
    }
}
