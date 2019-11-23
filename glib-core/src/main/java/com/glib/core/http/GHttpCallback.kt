package com.glib.core.http

import java.io.Serializable


interface GHttpCallback<HR : GHttpResponse<*>> : Serializable {
    fun onHttpResponse(response: HR)
}
