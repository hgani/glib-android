package com.gani.lib.http

import com.gani.lib.notification.Toast
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.ProgressIndicator


abstract class GRestCallback<HR : GHttpResponse<RR>, RR : GRestResponse>(  // To be used by child.
        private val indicator: ProgressIndicator, private val callback: (RR) -> Unit) : GHttpCallback<HR> {

    constructor(fragment: GFragment, callback: (RR) -> Unit) : this(fragment.indicator, callback)

    final override fun onHttpResponse(response: HR) {
        val r = response.rest

        try {
            onRestResponse(r)
        } finally {
            doFinally()
        }
    }

    fun onBeforeHttp() {
        indicator.showProgress()
    }

    open protected fun onRestResponse(response: RR) {
        displayMessage(response)
        callback(response)
    }

    protected fun doFinally() {
        indicator.hideProgress()
    }

    companion object {
        private fun displayMessage(r: GRestResponse): Boolean {
            val restData = r.result
            val message = restData["message"].string
            if (message != null) {
                Toast.center(message)
                return true
            }
            return false
        }
    }

    open class Default : GRestCallback<GHttpResponse.Default, GRestResponse> {
        constructor(indicator: ProgressIndicator, callback: (GRestResponse) -> Unit) : super(indicator, callback)
        constructor(fragment: GFragment, callback: (GRestResponse) -> Unit) : super(fragment, callback)
    }
}
