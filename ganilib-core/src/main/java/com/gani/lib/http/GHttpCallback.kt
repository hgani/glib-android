package com.gani.lib.http

import java.io.Serializable


interface GHttpCallback<HR : GHttpResponse<*>> : Serializable {
    fun onHttpResponse(response: HR)
}
