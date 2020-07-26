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
}
