package com.gani.lib.http

import java.io.Serializable


interface GHttpCallback<HR : GHttpResponse<*>, HE : GHttpError<*>> : Serializable {
    fun onHttpResponse(response: HR)
}
