package com.glib.core.http

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object UrlUtils {
    fun encodeUrl(url: String): String {
        try {
            return URLEncoder.encode(url, "UTF-8")
        } catch (uee: UnsupportedEncodingException) {
            throw IllegalArgumentException(uee)
        }

    }
    fun pathToUrl(path: String): String {
        return "${GHttp.instance.host}${path}"
    }
    fun jsonToHtml(url: String): String {
        var newUrl: String

        if (url.indexOf("?") > -1) {
            newUrl = url.replace(".json?", "?");
        } else {
            newUrl = url.replace(".json", "");
        }
        // As much as possible, try retaining the front fragment by matching the back, because
        // the front could be either a `?` or `&`
        newUrl = newUrl.replace("format=json&", "");
        // If no match, then we replace the front fragment
        newUrl = newUrl.replace(Regex("[\\&\\?]format=json"), "");

        return newUrl
    }
}
